package com.example.picfetcher

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MainActivityViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    private var STARTING_PAGE_INDEX = 0

    val imagesMutableLiveData: MutableLiveData<List<ApiPhoto>> = MutableLiveData()
    val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun nextPage() {
        STARTING_PAGE_INDEX += 20
    }

    fun isLastPage() = (STARTING_PAGE_INDEX == 251)

    fun fetchPicsLimited() {
        Log.d("End mech", "fetchPicsLimited() called")
        isLoadingMutableLiveData.value = true
        apiHelper.getPhotosLimited(STARTING_PAGE_INDEX)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("APIRXJAVA", " success = item 0 = ${response[1].id}")
                imagesMutableLiveData.value = response
                isLoadingMutableLiveData.value = false
            }, { error ->
                Log.d("APIRXJAVA", error.localizedMessage)
                isLoadingMutableLiveData.value = false
            }
            )
    }

}