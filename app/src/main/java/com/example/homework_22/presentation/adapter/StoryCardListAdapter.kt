package com.example.homework_22.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_22.databinding.StoryCardBinding
import com.example.homework_22.presentation.extension.loadImage
import com.example.homework_22.presentation.model.StoryUI

class StoryCardListAdapter :
    ListAdapter<StoryUI, StoryCardListAdapter.StoryCardViewHolder>(DiffUtil) {

    inner class StoryCardViewHolder(private val binding: StoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val item = currentList[adapterPosition]
            tvTitle.text = item.title
            ivStory.loadImage(item.cover)
        }
    }

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<StoryUI>() {
            override fun areItemsTheSame(oldItem: StoryUI, newItem: StoryUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryUI, newItem: StoryUI): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryCardViewHolder {
        return StoryCardViewHolder(
            StoryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoryCardViewHolder, position: Int) {
        holder.bind()
    }
}