package com.example.homework_22.data.remote.mapper

import com.example.homework_22.data.remote.model.StoryDto
import com.example.homework_22.domain.model.StoryModel

fun StoryDto.toDomain() =
    StoryModel(
        id = id,
        cover = cover,
        title = title
    )