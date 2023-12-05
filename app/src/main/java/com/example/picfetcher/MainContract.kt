package com.example.picfetcher

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