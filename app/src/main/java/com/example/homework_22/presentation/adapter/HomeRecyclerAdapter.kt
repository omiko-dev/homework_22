package com.example.homework_22.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_22.databinding.HomeViewBinding
import com.example.homework_22.presentation.model.PostUI
import com.example.homework_22.presentation.state.PostListState
import com.example.homework_22.presentation.state.StoryListState

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    private lateinit var storyAdapter: StoryCardListAdapter
    private lateinit var postAdapter: PostCardListAdapter
    private var postListState: PostListState = PostListState()
    private var storyListState: StoryListState = StoryListState()

    lateinit var onClick: (PostUI) -> Unit

    inner class HomeViewHolder(private val binding: HomeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            bindStoryAdapter()
            bindPostAdapter()
        }

        fun listener(){
            postAdapter.onClick = {
                onClick(it)
            }
        }

        private fun bindStoryAdapter() = with(binding) {
            storyAdapter = StoryCardListAdapter()
            storyRecycler.adapter = storyAdapter
            storyRecycler.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)

            storyListState.storyList?.let {
                storyAdapter.submitList(it)
            }
            if (storyListState.loader) {
                storyLoader.visibility = View.VISIBLE
            } else {
                storyLoader.visibility = View.GONE
            }
        }

        private fun bindPostAdapter() = with(binding) {
            postAdapter = PostCardListAdapter()
            with(binding) {
                postRecycler.adapter = postAdapter
                postRecycler.layoutManager = LinearLayoutManager(root.context)
            }
            postListState.postList?.let {
                postAdapter.submitList(it)
            }
            if (postListState.loader) {
                postLoader.visibility = View.VISIBLE
            } else {
                postLoader.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            bind()
            listener()
        }
    }

    fun setPostState(state: PostListState) {
        postListState = state
        notifyDataSetChanged()
    }

    fun setStoryState(state: StoryListState) {
        storyListState = state
        notifyDataSetChanged()
    }
}