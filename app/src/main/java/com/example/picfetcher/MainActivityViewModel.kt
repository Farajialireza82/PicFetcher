package com.example.picfetcher

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivityViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    private var STARTING_PAGE_INDEX = 1

    val imagesMutableLiveData: MutableLiveData<List<ApiPhoto>> = MutableLiveData()

    fun nextPage() {
        STARTING_PAGE_INDEX++
    }

    fun isLastPage() = (STARTING_PAGE_INDEX == 251)
    var isLoading = false


    fun fetchPics() {
        isLoading = true
        viewModelScope.launch {
            apiHelper.getPhotosLimited(STARTING_PAGE_INDEX).flowOn(Dispatchers.IO)

                .catch { e ->
                    isLoading = false
                    Log.e("MAVM", e.message.toString())

                }.collect {
                    isLoading = false
                    imagesMutableLiveData.value = it
                    Log.e("MAVM", "SUCCESS ${it[0].url}")

                }
        }
    }


}