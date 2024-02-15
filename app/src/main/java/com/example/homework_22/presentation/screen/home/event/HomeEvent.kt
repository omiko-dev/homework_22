package com.example.homework_22.presentation.screen.home.event

sealed class HomeEvent {
    data object GetHomeList: HomeEvent()
    data object GetStoryList: HomeEvent()
    data class NavigateToPost(val id: Int): HomeEvent()
}