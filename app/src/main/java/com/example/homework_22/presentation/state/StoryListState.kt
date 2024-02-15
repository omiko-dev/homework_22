package com.example.homework_22.presentation.state

import com.example.homework_22.presentation.model.StoryUI

data class StoryListState (
    val storyList: List<StoryUI>? = null,
    val error: String? = null,
    val loader: Boolean = false
)