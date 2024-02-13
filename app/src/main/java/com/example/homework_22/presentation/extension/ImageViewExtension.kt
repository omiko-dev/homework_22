package com.example.homework_22.presentation.extension

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.homework_22.R

fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.im_profile)
        .into(this)
}

private fun AppCompatImageView.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(left, top, right, bottom)
    layoutParams = params
}

fun AppCompatImageView.show(marginResId: Int) {
    val margin = resources.getDimensionPixelSize(marginResId)
    setMargins(0, 0, margin, 0)
    visibility = View.VISIBLE
}

fun AppCompatImageView.hide() {
    visibility = View.GONE
}