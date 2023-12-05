package com.example.picfetcher.ui

import com.example.picfetcher.network.ApiResponse
import com.example.picfetcher.network.ApiResponseCallback

class MainPresenter(
    private val mView: MainContract.View
) : MainContract.Presenter {

    private val model = MainModel()

    override fun fetchData() {
        mView.showProgressBar()
        model.loadData(ApiResultCallbackImpl())
    }

    private inner class ApiResultCallbackImpl() : ApiResponseCallback {
        override fun onApiResponse(apiResponse: ApiResponse) {
            mView.hideProgressBar()
            if (apiResponse.wasSuccessful) mView.updateDataToRecyclerView(apiResponse.data!!)
            else mView.showToast(apiResponse.error?.message.toString())
        }

    }
}