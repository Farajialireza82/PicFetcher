package com.example.picfetcher.ui.premierFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.picfetcher.R
import com.example.picfetcher.databinding.BottomSheetBinding
import com.example.picfetcher.databinding.FragmentPicsListBinding
import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.ui.bases.BaseFragment
import com.example.picfetcher.ui.recyclerView.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class PicsListFragment(
    val presenter: PicsListContract.Presenter
) : BaseFragment(), PicsListContract.View {


    private var _binding: FragmentPicsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: BottomSheetBinding
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pics_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPicsListBinding.bind(view)

        dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        presenter.attach(this)

        dialog = BottomSheetDialog(view.context, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogBinding.root)

        presenter.fetchPicsList()

        recyclerViewAdapter = RecyclerViewAdapter(presenter.getPicList())
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        addScrollListener()

        recyclerViewAdapter.setOnClickListener(object :
            RecyclerViewAdapter.OnClickListener {
            override fun onClick(apiPhoto: ApiPhoto) {
                presenter.fetchSinglePic(apiPhoto.id)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    override fun makeToast(string: String) {
        makeToast(string)
    }

    override fun showItemPage(photo: ApiPhoto) {
        showBottomSheet(photo)

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