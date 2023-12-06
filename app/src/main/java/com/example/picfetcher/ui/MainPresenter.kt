package com.example.picfetcher.ui

import com.example.picfetcher.network.APIService
import com.example.picfetcher.network.ApiResponse
import com.example.picfetcher.network.ApiResponseCallback

class MainPresenter(apiService: APIService): MainContract.Presenter {

    private val model = MainModel(apiService)
    private lateinit var mainView: MainContract.View


    override fun fetchData() {
        mainView.showProgressBar()
        model.loadData(ApiResultCallbackImpl())
    }

    override fun attach(mView: MainContract.View) {
        mainView = mView
    }

    private inner class ApiResultCallbackImpl() : ApiResponseCallback {
        override fun onApiResponse(apiResponse: ApiResponse) {
            mainView.hideProgressBar()
            if (apiResponse.wasSuccessful) mainView.updateDataToRecyclerView(apiResponse.data!!)
            else mainView.showToast(apiResponse.error?.message.toString())
        }

    }
}