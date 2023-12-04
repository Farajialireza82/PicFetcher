package com.example.picfetcher

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: APIService) : ApiHelper {
    override fun getPhotosLimited(firstPicId: Int): Observable<List<ApiPhoto>> =
        apiService.getPhotosLimited(firstPicId, 20)
}