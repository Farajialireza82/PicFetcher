package com.example.picfetcher.network

import com.example.picfetcher.model.ApiPhoto

interface ApiResponseCallback {
    fun onApiResponse(apiResponse: ApiResponse)
}
data class ApiResponse(
    var wasSuccessful:Boolean,
    var data: List<ApiPhoto>?,
    val error: Throwable?
)