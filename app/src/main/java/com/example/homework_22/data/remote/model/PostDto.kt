package com.example.homework_22.data.remote.model

import com.squareup.moshi.Json

data class PostDto(
    val id: String,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    @Json(name = "share_content")
    val shareContent: String,
    val owner: PostOwnerDto
)
