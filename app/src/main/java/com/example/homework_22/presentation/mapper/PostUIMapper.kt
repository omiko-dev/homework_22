package com.example.homework_22.presentation.mapper

import com.example.homework_22.domain.model.PostModel
import com.example.homework_22.presentation.model.PostOwnerUI
import com.example.homework_22.presentation.model.PostUI

fun PostModel.toPresenter() =
    PostUI(
        id = id,
        images = images,
        title = title,
        comments = comments,
        likes = likes,
        shareContent = shareContent,
        owner = PostOwnerUI(
            firstName = owner.firstName,
            lastName = owner.lastName,
            profile = owner.profile,
            postDate = owner.postDate
        )

    )