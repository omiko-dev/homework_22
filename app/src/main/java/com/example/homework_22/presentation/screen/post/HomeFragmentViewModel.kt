package com.example.homework_22.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.usecase.remote.GetPostListUseCase
import com.example.homework_22.domain.usecase.remote.GetStoryListUseCase
import com.example.homework_22.presentation.mapper.toPresenter
import com.example.homework_22.presentation.screen.post.event.HomeEvent
import com.example.homework_22.presentation.state.PostState
import com.example.homework_22.presentation.state.StoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val getStoryListUseCase: GetStoryListUseCase,
) : ViewModel() {

    private val _postListStateFlow = MutableStateFlow(PostState())
    val postListStateFlow get() = _postListStateFlow.asStateFlow()

    private val _storyListStateFlow = MutableStateFlow(StoryState())
    val storyListStateFlow get() = _storyListStateFlow.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetHomeList -> getPostList()
            is HomeEvent.GetStoryList -> getStoryList()
        }
    }

    private fun getPostList() {
        viewModelScope.launch {
            getPostListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _postListStateFlow.update { state ->
                            state.copy(
                                postList = resource.success.map { it.toPresenter() }
                            )
                        }
                    }

                    is Resource.Error -> {
                        _postListStateFlow.update { state ->
                            state.copy(
                                error = resource.error
                            )
                        }
                    }

                    is Resource.Loader -> {
                        _postListStateFlow.update { state ->
                            state.copy(
                                loader = resource.loader
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getStoryList() {
        viewModelScope.launch {
            getStoryListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _storyListStateFlow.update { state ->
                            state.copy(
                                storyList = resource.success.map { it.toPresenter() }
                            )
                        }
                    }

                    is Resource.Error -> {
                        _storyListStateFlow.update { state ->
                            state.copy(
                                error = resource.error
                            )
                        }
                    }

                    is Resource.Loader -> {
                        _storyListStateFlow.update { state ->
                            state.copy(
                                loader = resource.loader
                            )
                        }
                    }
                }
            }
        }
    }
}