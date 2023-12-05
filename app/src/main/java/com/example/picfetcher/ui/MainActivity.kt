package com.example.picfetcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var presenter: MainPresenter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = MainPresenter(this)

        recyclerViewAdapter = RecyclerViewAdapter(ArrayList())
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        presenter.fetchData()
        addScrollListener()

//        mainActivityViewModel.fetchPicsLimited()


    }

    override fun updateDataToRecyclerView(results: List<ApiPhoto>) {
        recyclerViewAdapter.updateListItems(results)
    }

    override fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.GONE
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun addScrollListener() {

        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {

                presenter.fetchData()
            }

            override fun isLastPage() = false


            override fun isLoading(): Boolean = isLoading
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
                ) loadMoreItems()
            }
        }

        protected abstract fun loadMoreItems()
        abstract fun isLastPage(): Boolean
        abstract fun isLoading(): Boolean
    }
}