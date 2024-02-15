package com.example.homework_22.presentation.screen.post.event

sealed class PostEvent {
    data class GetPost(val id: Int): PostEvent()
    data object NavigateToHome: PostEvent()
}