package com.example.homework_22.domain.usecase.remote

import com.example.homework_22.domain.repository.PostRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int) = postRepository.getPostById(id = id)
}