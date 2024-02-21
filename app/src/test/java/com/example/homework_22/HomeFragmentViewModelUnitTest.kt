package com.example.homework_22

import com.example.homework_22.data.common.Resource
import com.example.homework_22.domain.model.PostModel
import com.example.homework_22.domain.model.PostOwnerModel
import com.example.homework_22.domain.model.StoryModel
import com.example.homework_22.domain.usecase.remote.GetPostListUseCase
import com.example.homework_22.domain.usecase.remote.GetStoryListUseCase
import com.example.homework_22.presentation.mapper.toPresenter
import com.example.homework_22.presentation.screen.home.HomeFragmentViewModel
import com.example.homework_22.presentation.screen.home.event.HomeEvent
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

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class HomeFragmentViewModelUnitTest {

    @Mock
    private lateinit var getPostListUseCase: GetPostListUseCase

    @Mock
    private lateinit var getStoryListUseCase: GetStoryListUseCase

    private lateinit var viewModel: HomeFragmentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = HomeFragmentViewModel(getStoryListUseCase, getPostListUseCase)
    }


    @Test
    fun `get story list when GetStoryList event is passed`() = runTest {
        // Given
        whenever(getStoryListUseCase()).thenReturn(flowOf(Resource.Success(getStoryListUseCaseMock())))
        val event = HomeEvent.GetStoryList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.storyListStateFlow.take(2).toList()
        assertTrue(result.any { it -> it.storyList == getStoryListUseCaseMock().map { it.toPresenter() } && it.error == null })
    }

    @Test
    fun `get error when GetStoryList event is fail`() = runTest {
        // Given
        val testError = "test error"
        whenever(getStoryListUseCase()).thenReturn(flowOf(Resource.Error(testError)))
        val event = HomeEvent.GetStoryList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.storyListStateFlow.take(2).toList()
        assertTrue(result.any { it.error == testError })
    }

    @Test
    fun `get loader when GetStoryList event is in loading process`() = runTest {
        // Given
        val loading = true
        whenever(getStoryListUseCase()).thenReturn(flowOf(Resource.Loader(loader = loading)))
        val event = HomeEvent.GetStoryList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.storyListStateFlow.take(2).toList()
        assertTrue(result.any { it.loader == loading })
    }


    @Test
    fun `get post list when GetPostList event is passed`() = runTest {
        // Given
        whenever(getPostListUseCase()).thenReturn((flowOf(Resource.Success(getPostListUseCaseMock()))))
        val event = HomeEvent.GetPostList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.postListStateFlow.take(2).toList()
        assertTrue(result.any { it -> it.postList == getPostListUseCaseMock().map { it.toPresenter() } && it.error == null })
    }

    @Test
    fun `get error when GetPostList Event is fail`() = runTest {
        val testError = "test error"
        whenever(getPostListUseCase()).thenReturn(flowOf(Resource.Error(error = testError)))
        val event = HomeEvent.GetPostList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.postListStateFlow.take(2).toList()
        assertTrue(result.any { it -> it.error == testError})
    }

    @Test
    fun `get loader when GetPostList event is in loading process`() = runTest {
        // Given
        val loading = true
        whenever(getPostListUseCase()).thenReturn(flowOf(Resource.Loader(loader = loading)))
        val event = HomeEvent.GetPostList

        // When
        viewModel.onEvent(event = event)

        // Then
        val result = viewModel.postListStateFlow.take(2).toList()
        assertTrue(result.any { it.loader == loading })
    }

    @Test
    fun `navigate to post page when NavigationToPost event is passed`() = runTest {
        // Given
        val id = 1
        val navigationDestination = HomeFragmentViewModel.HomeUiEvent.NavigateToPost(id)
        val event = HomeEvent.NavigateToPost(id = id)

        // When
        viewModel.onEvent(event)

        // Then
        val result = viewModel.uiEvent.first()
        assertTrue(result == navigationDestination)

    }

    private fun getStoryListUseCaseMock(): List<StoryModel> =
        listOf(
            StoryModel(id = 1825, cover = "Keya", title = "Melisa"),
            StoryModel(id = 3436, cover = "Chani", title = "Gabriella")
        )


    private fun getPostListUseCaseMock(): List<PostModel> =
        listOf(
            PostModel(
                id = 1,
                images = listOf(),
                title = "Franklyn",
                comments = 8929,
                likes = 2726,
                shareContent = "Crysta",
                owner = PostOwnerModel(
                    firstName = "Nekia",
                    lastName = "Lavonne",
                    profile = null,
                    postDate = 8568L
                )
            ),
            PostModel(
                id = 2,
                images = listOf(),
                title = "Joanie",
                comments = 8574,
                likes = 7953,
                shareContent = "Dario",
                owner = PostOwnerModel(
                    firstName = "Kendal",
                    lastName = "Ozzie",
                    profile = null,
                    postDate = 5422L
                )
            )
        )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}