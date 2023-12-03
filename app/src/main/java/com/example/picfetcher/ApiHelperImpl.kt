package com.example.picfetcher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: APIService) : ApiHelper {
    override fun getPhotosLimited(firstPicId: Int): Flow<List<ApiPhoto>> = flow {

        emit(apiService.getPhotosLimited(firstPicId, 20))

    }
}