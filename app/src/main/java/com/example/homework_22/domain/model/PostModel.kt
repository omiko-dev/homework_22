package com.example.homework_22.domain.model

data class PostModel(
    val id: String,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: PostOwnerModel
)
