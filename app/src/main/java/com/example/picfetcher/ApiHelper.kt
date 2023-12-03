package com.example.picfetcher

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getPhotosLimited(firstPicId:Int): Flow<List<ApiPhoto>>

}