package com.example.picfetcher.model

import com.google.gson.annotations.SerializedName
data class ApiPhoto(
    @SerializedName("title")
    val title: String,
    @SerializedName("albumId")
    val albumId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)