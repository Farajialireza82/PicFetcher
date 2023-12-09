package com.example.picfetcher.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.picfetcher.BaseApplication
import com.example.picfetcher.R
import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.databinding.ActivityMainBinding
import com.example.picfetcher.databinding.BottomSheetBinding
import com.example.picfetcher.network.APIService
import com.example.picfetcher.ui.recyclerView.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: BottomSheetBinding
    private var isLoading = false


    lateinit var presenter: MainContract.Presenter

    @Inject
    lateinit var apiService: APIService


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        dialogBinding = BottomSheetBinding.inflate(layoutInflater)

        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogBinding.root)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (application as BaseApplication).getNetworkComponent().inject(this)

        presenter = MainPresenter(apiService)
        presenter.attach(this)

        recyclerViewAdapter = RecyclerViewAdapter(ArrayList())
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        presenter.fetchPicsList()
        addScrollListener()

        recyclerViewAdapter.setOnClickListener(object :
            RecyclerViewAdapter.OnClickListener {
            override fun onClick(apiPhoto: ApiPhoto) {
                presenter.fetchSinglePic(apiPhoto.id)
            }
        })


    }

    private fun showBottomSheet(photo: ApiPhoto) {
        Log.d("Data:", "Success= ${photo.title}")

        Glide.with(dialogBinding.root)
            .load(photo.url)
            .transform(RoundedCorners(25))
            .into(dialogBinding.imageView)

        dialogBinding.picTitleTextView.text = photo.title
        dialogBinding.imageIdTextView.text = photo.id.toString()
        dialogBinding.albumIdTextView.text = photo.albumId.toString()

        dialog.show()

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
        Snackbar.make(binding.recyclerView, string, Toast.LENGTH_SHORT).show()
    }

    override fun showItemPage(photo: ApiPhoto) {
        showBottomSheet(photo)
        Log.d("MainActivity", "showItemPage: called")
    }

    private fun addScrollListener() {

        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {

                presenter.fetchPicsList()
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