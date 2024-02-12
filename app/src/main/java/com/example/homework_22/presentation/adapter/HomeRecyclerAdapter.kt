package com.example.homework_22.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_22.databinding.HomeViewBinding
import com.example.homework_22.presentation.state.PostState
import com.example.homework_22.presentation.state.StoryState

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    private lateinit var storyAdapter: StoryCardListAdapter
    private lateinit var postAdapter: PostCardListAdapter
    private var postState: PostState = PostState()
    private var storyState: StoryState = StoryState()


    inner class HomeViewHolder(private val binding: HomeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            bindStoryAdapter()
            bindPostAdapter()

        }

        private fun bindStoryAdapter() = with(binding) {
            storyAdapter = StoryCardListAdapter()
            storyRecycler.adapter = storyAdapter
            storyRecycler.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)

            storyState.storyList?.let {
                storyAdapter.submitList(it)
            }
            if (storyState.loader) {
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
            postState.postList?.let {
                postAdapter.submitList(it)
            }
            if (postState.loader) {
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
        holder.bind()
    }

    fun setPostState(state: PostState) {
        postState = state
        notifyDataSetChanged()
    }

    fun setStoryState(state: StoryState) {
        storyState = state
        notifyDataSetChanged()
    }
}