package com.example.picfetcher.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picfetcher.ui.viewmodel.MainActivityViewModel
import com.example.picfetcher.ui.adapter.RecyclerViewAdapter
import com.example.picfetcher.api.ApiHelper
import com.example.picfetcher.api.ApiHelperImpl
import com.example.picfetcher.api.RetrofitClient
import com.example.picfetcher.databinding.ActivityMainBinding
import com.example.picfetcher.repository.MainRepository
import com.example.picfetcher.ui.intent.MainIntent
import com.example.picfetcher.ui.viewstate.MainUIState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        observeViewModel()

        mainActivityViewModel.imagesMutableLiveData.observe(this, Observer {
            recyclerViewAdapter.updateList(it)
        })


    }

    private fun setupUI() {
        recyclerViewAdapter = RecyclerViewAdapter(ArrayList())
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        addScrollListener()
    }

    private fun setupViewModel() {
        mainActivityViewModel =
            MainActivityViewModel(MainRepository(ApiHelperImpl(RetrofitClient.apiService)))
        lifecycleScope.launch {
            mainActivityViewModel.userIntent.send(MainIntent.FetchPics)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainActivityViewModel.state.collect {
                when (it) {
                    is MainUIState.Idle -> {}
                    is MainUIState.Pics -> {
                        binding.progressbar.visibility = View.GONE
                        recyclerViewAdapter.updateList(it.pics)
                        mainActivityViewModel.nextPage()
                    }
                    MainUIState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is MainUIState.Error -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun addScrollListener() {

        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {

                lifecycleScope.launch {
                    mainActivityViewModel.userIntent.send(MainIntent.FetchPics)
                }
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