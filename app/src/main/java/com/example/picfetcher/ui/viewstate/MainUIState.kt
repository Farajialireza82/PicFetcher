package com.example.picfetcher.ui.viewstate

import com.example.picfetcher.model.ApiPhoto

sealed class MainUIState {

    object Idle : MainUIState()
    object Loading : MainUIState()
    data class Pics(val pics: List<ApiPhoto>) : MainUIState()
    data class Error(val error: String) : MainUIState()

}