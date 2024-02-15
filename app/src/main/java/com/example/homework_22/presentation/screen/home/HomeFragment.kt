package com.example.homework_22.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_22.databinding.FragmentHomeBinding
import com.example.homework_22.presentation.adapter.HomeRecyclerAdapter
import com.example.homework_22.presentation.base.BaseFragment
import com.example.homework_22.presentation.screen.home.event.HomeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var homeAdapter: HomeRecyclerAdapter

    override fun bind() {
        bindHomeAdapter()
    }

    override fun event() = with(viewModel) {
        onEvent(HomeEvent.GetStoryList)
        onEvent(HomeEvent.GetHomeList)
    }

    override fun observe() {
        storyListObserve()
        postListObserve()
        uiEventObserve()
    }

    override fun listener() {
        homeAdapter.onClick = {
            viewModel.onEvent(HomeEvent.NavigateToPost(id = it.id.toInt()))
        }
    }

    private fun bindHomeAdapter() {
        homeAdapter = HomeRecyclerAdapter()
        with(binding) {
            recycler.adapter = homeAdapter
            recycler.layoutManager =
                LinearLayoutManager(requireContext())
        }
    }


    private fun storyListObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.storyListStateFlow.collect { state ->
                        homeAdapter.setStoryState(state)
                }
            }
        }
    }

    private fun postListObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postListStateFlow.collect { state ->
                    homeAdapter.setPostState(state)
                }
            }
        }
    }

    private fun uiEventObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: HomeFragmentViewModel.HomeUiEvent) {
        when (event) {
            is HomeFragmentViewModel.HomeUiEvent.NavigateToPost -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPostFragment(
                        event.id
                    )
                )
            }
        }
    }
}