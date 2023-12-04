package com.example.picfetcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.picfetcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var apiHelper: ApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerViewAdapter = RecyclerViewAdapter(ArrayList())


        apiHelper = ApiHelperImpl(RetrofitClient.apiService)

        mainActivityViewModel = MainActivityViewModel(apiHelper)

        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        addScrollListener()


        mainActivityViewModel.fetchPicsLimited()
        mainActivityViewModel.imagesMutableLiveData.observe(this, Observer {
            recyclerViewAdapter.updateList(it)
        })

        mainActivityViewModel.isLoadingMutableLiveData.observe(this, Observer {
            if (it == true) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.INVISIBLE
        })


    }

    private fun addScrollListener() {


        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {

                mainActivityViewModel.nextPage()
                mainActivityViewModel.fetchPicsLimited()
            }

            override fun isLastPage() = mainActivityViewModel.isLastPage()

            override fun isLoading(): Boolean = mainActivityViewModel.isLoadingMutableLiveData.value!!
        })
    }

    abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = layoutManager.childCount
            val totalItemCount: Int = layoutManager.itemCount
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()


            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {

                    loadMoreItems()
                }
            }
        }

        protected abstract fun loadMoreItems()
        abstract fun isLastPage(): Boolean
        abstract fun isLoading(): Boolean
    }
}