package com.example.homework_22.presentation.model

data class PostUI (
    val id: String,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: PostOwnerUI
)