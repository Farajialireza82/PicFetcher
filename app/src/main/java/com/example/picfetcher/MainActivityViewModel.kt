package com.example.picfetcher

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MainActivityViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    private var STARTING_PAGE_INDEX = 0

    val imagesMutableLiveData: MutableLiveData<List<ApiPhoto>> = MutableLiveData()

    fun nextPage() {
        STARTING_PAGE_INDEX += 20
    }

    fun isLastPage() = (STARTING_PAGE_INDEX == 251)
    var isLoading = false


    fun fetchPicsLimited() {
        isLoading = true
        viewModelScope.launch {
            apiHelper.getPhotosLimited(STARTING_PAGE_INDEX).flowOn(Dispatchers.IO)

                .catch { e ->
                    isLoading = false

                }.collect {
                    isLoading = false
                    imagesMutableLiveData.value = it

                }
        }
    }


}