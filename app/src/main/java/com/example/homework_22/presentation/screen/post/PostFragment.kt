package com.example.homework_22.presentation.screen.post

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homework_22.databinding.FragmentPostBinding
import com.example.homework_22.presentation.base.BaseFragment
import com.example.homework_22.presentation.extension.loadImage
import com.example.homework_22.presentation.screen.post.event.PostEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate) {
    private val viewModel: PostFragmentViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

    override fun event() {
        viewModel.onEvent(PostEvent.GetPost(id = args.id))
    }

    override fun observe() {
        observePost()
        observeUiEvent()
    }

    override fun listener() {
        binding.ivBack.setOnClickListener {
            viewModel.onEvent(PostEvent.NavigateToHome)
        }
    }

    private fun observePost() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postStateFlow.collect {
                    it.postList?.let { item ->
                        with(binding){
                            val fullName = "${item.owner.firstName}  ${item.owner.lastName}"
                            tvFullName.text = fullName
                            tvPostDate.text = item.owner.postDate
                            tvDesc.text = item.title
                            tvCommentNum.text = item.comments.toString()
                            tvLikeNum.text = item.likes.toString()
                            ivProfile.loadImage(item.owner.profile)
                        }
                    }

                    it.error?.let {

                    }

                    binding.loader.visibility = if (it.loader) View.VISIBLE else View.INVISIBLE
                }
            }
        }
    }

    private fun observeUiEvent(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: PostFragmentViewModel.PostUiEvent){
        when(event){
            is PostFragmentViewModel.PostUiEvent.NavigateToHome -> {
                findNavController().popBackStack()
            }
        }
    }
}