package com.example.homework_22.data.repository

import com.example.homework_22.data.common.HandleResource
import com.example.homework_22.data.common.Resource
import com.example.homework_22.data.common.resourceMapper
import com.example.homework_22.data.remote.mapper.toDomain
import com.example.homework_22.data.remote.service.StoryService
import com.example.homework_22.domain.model.StoryModel
import com.example.homework_22.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyService: StoryService,
    private val handleResource: HandleResource,
) : StoryRepository {
    override suspend fun getStoryList(): Flow<Resource<List<StoryModel>>> =
        handleResource.handleResource {
            storyService.getStoreList()
        }.map { resource ->
            resource.resourceMapper { storyDtoList ->
                storyDtoList.map {
                    it.toDomain()
                }
            }
        }
}