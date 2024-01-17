package com.example.picfetcher.model

import com.squareup.moshi.Json

data class ApiPhoto(
    @field:Json(name = "albumId")
    val albumId: Int,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "thumbnailUrl")
    val thumbnailUrl: String
)