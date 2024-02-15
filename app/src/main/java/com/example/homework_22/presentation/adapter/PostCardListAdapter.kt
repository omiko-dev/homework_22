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

    lateinit var onClick: (PostUI) -> Unit

    inner class PostCardViewHolder(private val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val item = currentList[adapterPosition]

            val fullName = "${item.owner.firstName}  ${item.owner.lastName}"
            tvFullName.text = fullName
            tvPostDate.text = item.owner.postDate
            tvDesc.text = item.title
            tvCommentNum.text = item.comments.toString()
            tvLikeNum.text = item.likes.toString()

            ivProfile.loadImage(item.owner.profile)
//            postImageRecyclerAdapter = PostImageRecyclerAdapter()
//            recycler.adapter = postImageRecyclerAdapter
//            var lm = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//
//            val spanCount = 2 // Number of columns in your grid
//            val spacing = 10 // spacing between grid items, if any
//            val aspectRatio = image.height.toFloat() / image.width.toFloat()
//            val itemHeight = (itemWidth * aspectRatio).toInt()

//            val itemWidth = (400 - ((spanCount + 1) * spacing)) / spanCount
//            recycler.layoutManager = lm
//            item.images?.let {
//                postImageRecyclerAdapter.setPostImage(it)
//            }

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

        fun listener(){
            binding.root.setOnClickListener {
                onClick(currentList[adapterPosition])
            }
        }

        private fun getImageAtIndex(item: PostUI, index: Int): String? {
            return item.images?.getOrNull(index)
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
        with(holder){
            bind()
            listener()
        }
    }
}