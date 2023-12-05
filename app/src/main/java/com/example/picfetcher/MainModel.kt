package com.example.picfetcher

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainModel : MainContract.Model {

    private val apiService = RetrofitClient.apiService
    private var STARTING_PAGE_INDEX = 0
    override fun loadData(apiResponseCallback: ApiResponseCallback) {

        apiService.getPhotosLimited(STARTING_PAGE_INDEX)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                STARTING_PAGE_INDEX += 20
                apiResponseCallback.onApiResponse(ApiResponse(true, response, null))


            }, {
                apiResponseCallback.onApiResponse(ApiResponse(false, null, it))
            }
            )
    }

}
