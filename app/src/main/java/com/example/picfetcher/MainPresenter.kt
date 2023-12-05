package com.example.picfetcher

import android.util.Log

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