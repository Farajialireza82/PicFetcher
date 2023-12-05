package com.example.picfetcher.ui

import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.network.ApiResponseCallback

interface MainContract {

    interface View{
        fun updateDataToRecyclerView(results: List<ApiPhoto>)
        fun showProgressBar()
        fun hideProgressBar()

        fun showToast(string: String)
    }

    interface Presenter{
        fun fetchData()
    }

    interface Model{
        fun loadData(apiResponseCallback: ApiResponseCallback)
    }

}