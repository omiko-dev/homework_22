package com.example.homework_22.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.usecase.remote.GetPostListUseCase
import com.example.homework_22.domain.usecase.remote.GetStoryListUseCase
import com.example.homework_22.presentation.mapper.toPresenter
import com.example.homework_22.presentation.screen.home.event.HomeEvent
import com.example.homework_22.presentation.state.PostListState
import com.example.homework_22.presentation.state.StoryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getStoryListUseCase: GetStoryListUseCase,
    private val getPostListUseCase: GetPostListUseCase
) : ViewModel() {

    private val _postListStateFlow = MutableStateFlow(PostListState())
    val postListStateFlow get() = _postListStateFlow.asStateFlow()

    private val _storyListStateFlow = MutableStateFlow(StoryListState())
    val storyListStateFlow get() = _storyListStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetPostList -> getPostList()
            is HomeEvent.GetStoryList -> getStoryList()
            is HomeEvent.NavigateToPost -> navigateToPost(event.id)
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

    private fun navigateToPost(id: Int){
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToPost(id = id))
        }
    }

    sealed interface HomeUiEvent{
        data class NavigateToPost(val id: Int): HomeUiEvent
    }
}