package com.example.picfetcher.repository

import com.example.picfetcher.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun fetchPics(pageNumber: Int) = apiHelper.getPhotosLimited(pageNumber)

}