package com.example.picfetcher

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getPhotos(): Flow<List<ApiPhoto>>

    fun getPhotosLimited(pageNumber:Int): Flow<List<ApiPhoto>>

}