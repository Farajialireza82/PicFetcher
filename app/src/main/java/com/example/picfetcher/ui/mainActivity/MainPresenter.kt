package com.example.picfetcher.ui.mainActivity

import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.network.APIService
import com.example.picfetcher.network.ApiListResponse
import com.example.picfetcher.network.ApiResponseCallback
import com.example.picfetcher.network.ApiSingleResponse

class MainPresenter(apiService: APIService) : MainContract.Presenter {

    private val model = MainModel(apiService)
    private lateinit var mainView: MainContract.View


    override fun fetchPicsList() {
        mainView.showProgressBar()
        model.loadPicsList(ApiResultCallbackImpl())
    }

    override fun fetchSinglePic(picId: Int) {
        mainView.showProgressBar()
        model.loadSinglePicture(picId, ApiResultCallbackImpl())
    }

    override fun showItemDetails(apiPhoto: ApiPhoto) {
        mainView.showItemPage(apiPhoto)
    }

    override fun attach(mView: MainContract.View) {
        mainView = mView
    }


    private inner class ApiResultCallbackImpl() : ApiResponseCallback {
        override fun onApiResponse(apiListResponse: ApiListResponse) {
            mainView.hideProgressBar()
            if (apiListResponse.wasSuccessful) mainView.updateDataToRecyclerView(apiListResponse.data!!)
            else mainView.showToast(apiListResponse.error?.message.toString())
        }

        override fun onApiResponse(apiSingleResponse: ApiSingleResponse) {
            mainView.hideProgressBar()
            if (apiSingleResponse.wasSuccessful) mainView.showItemPage(apiSingleResponse.data!!)
            else mainView.showToast(apiSingleResponse.error?.message.toString())

        }

    }
}