package com.example.picfetcher.api

import com.example.picfetcher.model.ApiPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: APIService) : ApiHelper {
    override suspend fun getPhotosLimited(firstPicId: Int): List<ApiPhoto> =
        apiService.getPhotosLimited(firstPicId, 20)
}