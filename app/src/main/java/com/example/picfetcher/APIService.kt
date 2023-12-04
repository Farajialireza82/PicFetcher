package com.example.picfetcher

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/photos/")
    fun getPhotosLimited(
        @Query("_start") first:Int,
        @Query("_limit") last:Int
    ): Observable<List<ApiPhoto>>

}