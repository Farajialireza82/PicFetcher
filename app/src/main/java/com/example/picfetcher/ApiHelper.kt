package com.example.picfetcher

import androidx.paging.PagingSource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

   fun getPhotosLimited(firstPicId:Int): Observable<List<ApiPhoto>>

}