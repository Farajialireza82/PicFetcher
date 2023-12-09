package com.example.picfetcher.ui.mainActivity

import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.network.ApiResponseCallback

interface MainContract {

    interface View{
        fun updateDataToRecyclerView(results: List<ApiPhoto>)
        fun showProgressBar()
        fun hideProgressBar()

        fun showToast(string: String)

        fun showItemPage(photo: ApiPhoto)
    }

    interface Presenter{

        fun fetchPicsList()

        fun fetchSinglePic(picId: Int)

        fun showItemDetails(apiPhoto: ApiPhoto)

        fun attach(mView: View)
    }

    interface Model{
        fun loadPicsList(apiResponseCallback: ApiResponseCallback)

        fun loadSinglePicture(picId:Int, apiResponseCallback: ApiResponseCallback)
    }

}
