package com.example.homework_22.domain.usecase.remote

import com.example.homework_22.domain.repository.PostRepository
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke() = postRepository.getPostList()
}