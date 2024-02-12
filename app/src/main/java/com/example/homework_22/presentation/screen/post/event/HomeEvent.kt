package com.example.homework_22.presentation.screen.post.event

sealed class HomeEvent {
    data object GetHomeList: HomeEvent()
    data object GetStoryList: HomeEvent()
}