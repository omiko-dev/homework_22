package com.example.homework_22.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_22.databinding.PostCardBinding
import com.example.homework_22.presentation.extension.loadImage
import com.example.homework_22.presentation.model.PostUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostCardListAdapter : ListAdapter<PostUI, PostCardListAdapter.PostCardViewHolder>(DiffUtil) {
    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<PostUI>() {
            override fun areItemsTheSame(oldItem: PostUI, newItem: PostUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostUI, newItem: PostUI): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PostCardViewHolder(private val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind() = with(binding){
                val item = currentList[adapterPosition]
                desc.text = item.shareContent
                tvCommentNum.text = item.comments.toString()
                val fullName = item.owner.firstName + " " + item.owner.lastName
                tvName.text = fullName
                tvLikesNum.text = item.likes.toString()
                tvDate.text = convertUnixTimestamp(item.owner.postDate)
                ivProfile.loadImage(item.owner.profile)
            }
    }

    fun convertUnixTimestamp(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val formatter = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
        return formatter.format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCardViewHolder {
        return PostCardViewHolder(
            PostCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostCardViewHolder, position: Int) {
        holder.bind()
    }
}