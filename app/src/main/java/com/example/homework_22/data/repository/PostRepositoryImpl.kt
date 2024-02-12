package com.example.homework_22.data.repository

import com.example.homework_22.data.common.HandleResource
import com.example.homework_22.data.common.Resource
import com.example.homework_22.data.common.resourceMapper
import com.example.homework_22.data.remote.mapper.toDomain
import com.example.homework_22.data.remote.service.PostService
import com.example.homework_22.domain.model.PostModel
import com.example.homework_22.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val handleResource: HandleResource
): PostRepository {
    override suspend fun getPostList(): Flow<Resource<List<PostModel>>> =
        handleResource.handleResource {
            postService.getPostList()
        }.map { resource ->
            resource.resourceMapper { postDtoList ->
                postDtoList.map {
                    it.toDomain()
                }
            }
        }
}