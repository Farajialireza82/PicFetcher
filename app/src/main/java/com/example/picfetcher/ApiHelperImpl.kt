package com.example.picfetcher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: APIService) : ApiHelper {
    override fun getPhotos(): Flow<List<ApiPhoto>> = flow {
        emit(apiService.getPhotos())
    }

    override fun getPhotosLimited(pageNumber: Int): Flow<List<ApiPhoto>> = flow {

        emit(apiService.getPhotosLimited((pageNumber - 1) * 20, pageNumber * 20))
    }
}