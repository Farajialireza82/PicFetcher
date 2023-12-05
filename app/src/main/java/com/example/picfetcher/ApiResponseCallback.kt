package com.example.picfetcher

interface ApiResponseCallback {
    fun onApiResponse(apiResponse: ApiResponse)
}
data class ApiResponse(
    var wasSuccessful:Boolean,
    var data: List<ApiPhoto>?,
    val error: Throwable?
)