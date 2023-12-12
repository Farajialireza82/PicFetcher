package com.example.picfetcher.ui.premierFragment

import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.network.APIService
import com.example.picfetcher.network.ApiListResponse
import com.example.picfetcher.network.ApiResponseCallback
import com.example.picfetcher.network.ApiSingleResponse
import com.example.picfetcher.ui.bases.BasePresenter

class PicsListPresenter(val apiService: APIService) : PicsListContract.Presenter, BasePresenter() {

    private lateinit var mainView: PicsListContract.View
    private val _arrayList = ArrayList<ApiPhoto>()

    override fun getPicList(): ArrayList<ApiPhoto> {
        return _arrayList
    }

    override fun fetchPicsList() {
        mainView.showProgressBar()
        loadPicsList(ApiResultCallbackImpl(), apiService)
    }

    override fun fetchSinglePic(picId: Int) {
        mainView.showProgressBar()
        loadSinglePicture(picId, ApiResultCallbackImpl(), apiService)
    }

    override fun showItemDetails(apiPhoto: ApiPhoto) {
        mainView.showItemPage(apiPhoto)
    }

    override fun attach(mView: PicsListContract.View) {
        mainView = mView
    }


    private inner class ApiResultCallbackImpl() : ApiResponseCallback {
        override fun onApiResponse(apiListResponse: ApiListResponse) {
            mainView.hideProgressBar()
            if (apiListResponse.wasSuccessful) mainView.updateDataToRecyclerView(apiListResponse.data!!)
            else mainView.makeToast(apiListResponse.error?.message.toString())
        }

        override fun onApiResponse(apiSingleResponse: ApiSingleResponse) {
            mainView.hideProgressBar()
            if (apiSingleResponse.wasSuccessful) mainView.showItemPage(apiSingleResponse.data!!)
            else mainView.makeToast(apiSingleResponse.error?.message.toString())

        }
    }
}