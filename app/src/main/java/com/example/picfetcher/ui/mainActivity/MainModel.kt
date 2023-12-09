package com.example.picfetcher.ui.mainActivity

import android.annotation.SuppressLint
import com.example.picfetcher.network.APIService
import com.example.picfetcher.network.ApiListResponse
import com.example.picfetcher.network.ApiResponseCallback
import com.example.picfetcher.network.ApiSingleResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainModel(val apiService:APIService) : MainContract.Model {

//    var apiService: APIService = RetrofitClient.apiService
    private var STARTING_PAGE_INDEX = 0


    @SuppressLint("CheckResult")
    override fun loadPicsList(apiResponseCallback: ApiResponseCallback) {

        apiService.getPhotosLimited(STARTING_PAGE_INDEX)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                STARTING_PAGE_INDEX += 20
                apiResponseCallback.onApiResponse(ApiListResponse(true, response, null))
            }, {
                apiResponseCallback.onApiResponse(ApiListResponse(false, null, it))
            }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadSinglePicture(picId: Int, apiResponseCallback: ApiResponseCallback) {
        apiService.getPhotoWithId(picId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                apiResponseCallback.onApiResponse(ApiSingleResponse(true, response, null))
            }, {
                apiResponseCallback.onApiResponse(ApiSingleResponse(false, null, it))
            }
            )

    }

}
