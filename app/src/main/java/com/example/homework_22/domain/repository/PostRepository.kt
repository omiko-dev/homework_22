package com.example.homework_22.domain.repository

import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.model.PostModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPostList(): Flow<Resource<List<PostModel>>>
}