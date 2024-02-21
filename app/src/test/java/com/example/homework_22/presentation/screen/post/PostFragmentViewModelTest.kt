package com.example.homework_22.presentation.screen.post

import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.model.PostModel
import com.example.homework_22.domain.model.PostOwnerModel
import com.example.homework_22.domain.usecase.remote.GetPostByIdUseCase
import com.example.homework_22.presentation.mapper.toPresenter
import com.example.homework_22.presentation.screen.post.event.PostEvent
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PostFragmentViewModelTest {

    @Mock
    private lateinit var getPostByIdUseCase: GetPostByIdUseCase

    private lateinit var viewModel: PostFragmentViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = PostFragmentViewModel(getPostByIdUseCase)
    }

    @Test
    fun `get post when GetPost event is passed`() = runTest {
        val id = getPostByIdUseCaseMock().id
        whenever(getPostByIdUseCase.invoke(id)).thenReturn(flowOf(Resource.Success(getPostByIdUseCaseMock())))

        // When
        viewModel.onEvent(PostEvent.GetPost(id))

        // Then
        val result = viewModel.postStateFlow.take(2).toList()
        assertTrue(result.any { it.postList == getPostByIdUseCaseMock().toPresenter() && it.error == null })
    }

    @Test
    fun `get error when GetPost event is fail`() = runTest {
        // Given
        val id = getPostByIdUseCaseMock().id
        val error = "error message"
        whenever(getPostByIdUseCase(id)).thenReturn(flowOf(Resource.Error(error)))

        // When
        viewModel.onEvent(PostEvent.GetPost(id))

        // Then
        val result = viewModel.postStateFlow.take(2).toList()
        assertTrue(result.any { it.error == error})
    }

    @Test
    fun `get loader when GetPost event is loading process`() = runTest {
        // Given
        val id = getPostByIdUseCaseMock().id
        val isLoading = true
        whenever(getPostByIdUseCase(id)).thenReturn(flowOf(Resource.Loader(loader = isLoading)))

        // When
        viewModel.onEvent(PostEvent.GetPost(id))

        // Then
        val result = viewModel.postStateFlow.take(2).toList()
        assertTrue(result.any { it.loader == isLoading })
    }

    @Test
    fun `navigate to home when NavigateToHome event is passed`() = runTest {

        // Given
        val navigationDestination = PostFragmentViewModel.PostUiEvent.NavigateToHome
        val event = PostEvent.NavigateToHome

        // When
        viewModel.onEvent(event)

        // Then
        val result = viewModel.uiEvent.first()
        assertTrue(result == navigationDestination)

    }



    private fun getPostByIdUseCaseMock(): PostModel =
        PostModel(
            id = 1,
            images = listOf(),
            title = "Kaylie",
            comments = 174,
            likes = 9470,
            shareContent = "Joslyn",
            owner = PostOwnerModel(
                firstName = "Chuck",
                lastName = "Tena",
                profile = null,
                postDate = 6982L
            )
        )

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}