package com.example.picfetcher.ui

import android.annotation.SuppressLint
import android.app.Application
import com.example.picfetcher.BaseApplication
import com.example.picfetcher.network.APIService
import com.example.picfetcher.network.ApiResponse
import com.example.picfetcher.network.ApiResponseCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainModel(val apiService:APIService) : MainContract.Model {

//    var apiService: APIService = RetrofitClient.apiService
    private var STARTING_PAGE_INDEX = 0


    @SuppressLint("CheckResult")
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
