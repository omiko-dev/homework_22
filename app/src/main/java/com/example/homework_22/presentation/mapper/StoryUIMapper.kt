package com.example.homework_22.presentation.mapper

import com.example.homework_22.domain.model.StoryModel
import com.example.homework_22.presentation.model.StoryUI

fun StoryModel.toPresenter() =
    StoryUI(
        id = id,
        cover = cover,
        title = title
    )