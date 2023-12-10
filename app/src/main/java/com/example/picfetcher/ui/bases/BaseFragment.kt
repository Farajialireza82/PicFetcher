package com.example.picfetcher.ui.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picfetcher.R
import com.example.picfetcher.ui.premierFragment.PicsListFragment
import com.example.picfetcher.ui.recyclerView.PaginationScrollListener
import com.example.picfetcher.ui.recyclerView.RecyclerViewAdapter

abstract class BaseFragment : Fragment() {
    fun recyclerViewSetOnClickListener(
        recyclerViewAdapter: RecyclerViewAdapter,
        onClickFunc: RecyclerViewAdapter.OnClickListener
    ) {
        recyclerViewAdapter.setOnClickListener(onClickFunc)
    }

    fun recyclerViewAddOnScrollListener(
        recyclerView: RecyclerView,
        paginationScrollListener: PaginationScrollListener
    ) {
        recyclerView.addOnScrollListener(paginationScrollListener)
    }

}