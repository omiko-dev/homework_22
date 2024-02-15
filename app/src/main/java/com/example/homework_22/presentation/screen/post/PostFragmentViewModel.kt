package com.example.homework_22.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.usecase.remote.GetRemoteUseCase
import com.example.homework_22.presentation.mapper.toPresenter
import com.example.homework_22.presentation.screen.post.event.PostEvent
import com.example.homework_22.presentation.state.PostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostFragmentViewModel @Inject constructor(
    private val getRemoteUseCase: GetRemoteUseCase,
) : ViewModel() {

    private val _postStateFlow = MutableStateFlow(PostState())
    val postStateFlow get() = _postStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PostUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onEvent(event: PostEvent){
        when(event){
            is PostEvent.GetPost -> getPost(event.id)
            is PostEvent.NavigateToHome -> navigateToHome()
        }
    }

    private fun getPost(id: Int) {
        viewModelScope.launch {
            getRemoteUseCase.getPostByIdUseCase(id = id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _postStateFlow.update { state ->
                            state.copy(
                                postList = resource.success.toPresenter()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _postStateFlow.update { state ->
                            state.copy(
                                error = resource.error
                            )
                        }
                    }
                    is Resource.Loader -> {
                        _postStateFlow.update { state ->
                            state.copy(
                                loader = resource.loader
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateToHome(){
        viewModelScope.launch {
            _uiEvent.emit(PostUiEvent.NavigateToHome)
        }
    }

    sealed interface PostUiEvent{
        data object NavigateToHome: PostUiEvent
    }
}