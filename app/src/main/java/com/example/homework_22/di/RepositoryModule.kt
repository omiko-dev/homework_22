package com.example.homework_22.di

import com.example.homework_22.data.common.HandleResource
import com.example.homework_22.data.remote.service.PostService
import com.example.homework_22.data.remote.service.StoryService
import com.example.homework_22.data.repository.PostRepositoryImpl
import com.example.homework_22.data.repository.StoryRepositoryImpl
import com.example.homework_22.domain.repository.PostRepository
import com.example.homework_22.domain.repository.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(
        postService: PostService,
        handleResource: HandleResource,
    ): PostRepository =
        PostRepositoryImpl(postService = postService, handleResource = handleResource)

    @Provides
    @Singleton
    fun provideStoryRepository(
        storyService: StoryService,
        handleResource: HandleResource,
    ): StoryRepository =
        StoryRepositoryImpl(storyService = storyService, handleResource = handleResource)
}