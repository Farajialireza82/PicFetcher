package com.example.picfetcher

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/photos/")
    suspend fun getPhotosLimited(
        @Query("_start") first:Int,
        @Query("_limit") last:Int
    ): List<ApiPhoto>

}