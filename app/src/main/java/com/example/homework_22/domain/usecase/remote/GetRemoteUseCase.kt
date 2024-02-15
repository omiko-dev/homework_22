package com.example.homework_22.domain.usecase.remote

import javax.inject.Inject

data class GetRemoteUseCase @Inject constructor (
    val getPostListUseCase: GetPostListUseCase,
    val getPostByIdUseCase: GetPostByIdUseCase,
    val getStoryListUseCase: GetStoryListUseCase
)