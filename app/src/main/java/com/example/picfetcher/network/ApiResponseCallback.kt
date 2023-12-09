package com.example.picfetcher.network

import com.example.picfetcher.model.ApiPhoto

interface ApiResponseCallback {
    fun onApiResponse(apiListResponse: ApiListResponse)
    fun onApiResponse(apiSingleResponse: ApiSingleResponse)
}

data class ApiListResponse(
    var wasSuccessful: Boolean,
    var data: List<ApiPhoto>?,
    val error: Throwable?
)

data class ApiSingleResponse(
    var wasSuccessful: Boolean,
    var data: ApiPhoto?,
    val error: Throwable?
)