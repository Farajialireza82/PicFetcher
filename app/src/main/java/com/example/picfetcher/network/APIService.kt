package com.example.picfetcher.network

import com.example.picfetcher.model.ApiPhoto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/photos/")
    fun getPhotosLimited(
        @Query("_start") first:Int,
        @Query("_limit") last:Int = 20
    ): Observable<List<ApiPhoto>>

    @GET("/photos/{id}")
    fun getPhotoWithId(
        @Path("id") picId:Int
    ): Observable<ApiPhoto>

}