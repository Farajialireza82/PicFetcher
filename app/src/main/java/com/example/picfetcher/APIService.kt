package com.example.picfetcher

import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/photos/")
    suspend fun getPhotos(): List<ApiPhoto>

    @GET("/photos/")
    suspend fun getPhotosLimited(
        @Query("_start") first:Int,
        @Query("_end") last:Int
    ): List<ApiPhoto>

}