package com.example.homework_22.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_22.R
import com.example.homework_22.databinding.PostCardBinding
import com.example.homework_22.presentation.extension.hide
import com.example.homework_22.presentation.extension.loadImage
import com.example.homework_22.presentation.extension.show
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
        fun bind() = with(binding) {
            val item = currentList[adapterPosition]

            val fullName = "${item.owner.firstName}  ${item.owner.lastName}"
            tvFullName.text = fullName
            tvPostDate.text = convertToDate(item.owner.postDate)
            tvDesc.text = item.shareContent
            tvCommentNum.text = item.comments.toString()
            tvLikeNum.text = item.likes.toString()

            ivProfile.loadImage(item.owner.profile)
            ivPostOne.loadImage(getImageAtIndex(item, 0))
            ivPostTwo.loadImage(getImageAtIndex(item, 1))
            ivPostThree.loadImage(getImageAtIndex(item, 2))

            when (item.images?.size) {
                null -> {
                    ivPostThree.hide()
                    ivPostTwo.hide()
                    ivPostOne.hide()
                }

                1 -> {
                    ivPostThree.hide()
                    ivPostTwo.hide()
                }

                2 -> {
                    ivPostThree.hide()
                    ivPostOne.show(R.dimen.margin_10dp)
                }

                3 -> {
                    ivPostOne.show(R.dimen.margin_10dp)
                    ivPostTwo.show(R.dimen.margin_12dp)
                }
            }
        }

        private fun getImageAtIndex(item: PostUI, index: Int): String? {
            return item.images?.getOrNull(index)
        }

        private fun convertToDate(timestamp: Long): String {
            val date = Date(timestamp * 1000)
            val getDate = SimpleDateFormat(EDataFormat.DD_MMMM_H_MM.pattern, Locale.getDefault())
            return getDate.format(date)
        }
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

    private enum class EDataFormat(val pattern: String) {
        DD_MMMM_H_MM("dd MMMM 'at' h:mm a")
    }
}