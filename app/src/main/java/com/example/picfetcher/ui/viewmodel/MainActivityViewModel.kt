package com.example.picfetcher.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picfetcher.api.ApiHelper
import com.example.picfetcher.api.ApiHelperImpl
import com.example.picfetcher.api.RetrofitClient
import com.example.picfetcher.model.ApiPhoto
import com.example.picfetcher.repository.MainRepository
import com.example.picfetcher.ui.intent.MainIntent
import com.example.picfetcher.ui.viewstate.MainUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MainActivityViewModel(
    private val repository: MainRepository
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainUIState>(MainUIState.Idle)
    val state: StateFlow<MainUIState>
        get() = _state

    var STARTING_PAGE_INDEX = 0
    val imagesMutableLiveData: MutableLiveData<List<ApiPhoto>> = MutableLiveData()

    init {
        handleIntent()
    }

    fun nextPage() {
        STARTING_PAGE_INDEX += 20
    }

    fun isLastPage() = (STARTING_PAGE_INDEX == 251)
    var isLoading = false

    private fun handleIntent(){
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect{
                when(it){
                    is MainIntent.FetchPics -> fetchPics()
                }
            }
        }
    }


    private fun fetchPics() {
        viewModelScope.launch {
            _state.value = MainUIState.Loading
            _state.value = try {
                MainUIState.Pics(repository.fetchPics(STARTING_PAGE_INDEX))
            } catch (e: Exception) {
                MainUIState.Error(e.localizedMessage)
            }
        }
    }


}