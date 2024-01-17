package com.example.picfetcher.api

import com.example.picfetcher.model.ApiPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/photos/")
    suspend fun getPhotosLimited(
        @Query("_start") first:Int,
        @Query("_limit") last:Int
    ): List<ApiPhoto>

}