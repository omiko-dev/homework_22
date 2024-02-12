package com.example.homework_22.domain.usecase.remote

import com.example.homework_22.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoryListUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) {
    suspend operator fun invoke() = storyRepository.getStoryList()
}