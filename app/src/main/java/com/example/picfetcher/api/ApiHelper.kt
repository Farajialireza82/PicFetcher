package com.example.picfetcher.api

import com.example.picfetcher.model.ApiPhoto
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    suspend fun getPhotosLimited(firstPicId:Int): List<ApiPhoto>

}