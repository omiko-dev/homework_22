package com.example.homework_22.presentation.state

import com.example.homework_22.presentation.model.PostUI

data class PostState (
    val postList: List<PostUI>? = null,
    val error: String? = null,
    val loader: Boolean = false
)