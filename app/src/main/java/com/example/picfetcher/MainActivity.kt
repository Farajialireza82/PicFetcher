package com.example.picfetcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picfetcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerViewAdapter = RecyclerViewAdapter(ArrayList())


        val apiHelper = ApiHelperImpl(RetrofitClient.apiService)

        mainActivityViewModel = MainActivityViewModel(apiHelper)

        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        addScrollListener()


        mainActivityViewModel.fetchPics()
        mainActivityViewModel.imagesMutableLiveData.observe(this, Observer {
            recyclerViewAdapter.updateList(it)
        })


    }

    private fun addScrollListener() {
        Log.e("pagination", "addScrollListener() Called")

        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                Log.e("pagination", "loadMoreItems() Called")
                mainActivityViewModel.nextPage()
                mainActivityViewModel.fetchPics()
            }

            override fun isLastPage() = mainActivityViewModel.isLastPage()

            override fun isLoading() = mainActivityViewModel.isLoading
        })
    }

    abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = layoutManager.childCount
            val totalItemCount: Int = layoutManager.itemCount
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
            Log.e("pagination", "isLoading is ${isLoading()}")
            Log.e("pagination", "isLastPage is ${isLastPage()}")

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    Log.e("pagination", "onScrolled() Called")
                    loadMoreItems()
                }
            }
        }

        protected abstract fun loadMoreItems()
        abstract fun isLastPage(): Boolean
        abstract fun isLoading(): Boolean
    }
}