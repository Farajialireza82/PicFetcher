package com.example.picfetcher

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ApiPhoto(
    @field:Json(name = "albumId")
    @SerializedName("albumId")
    val albumId: Int,
    @field:Json(name = "id")
    @SerializedName("id")
    val id: Int,
    @field:Json(name = "url")
    @SerializedName("url")
    val url: String,
    @field:Json(name = "thumbnailUrl")
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)