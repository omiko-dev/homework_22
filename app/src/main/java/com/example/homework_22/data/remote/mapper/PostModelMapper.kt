package com.example.homework_22.data.remote.mapper

import com.example.homework_22.data.remote.model.PostDto
import com.example.homework_22.domain.model.PostModel
import com.example.homework_22.domain.model.PostOwnerModel

fun PostDto.toDomain(): PostModel {
    return PostModel(
        id = id,
        images = images,
        title = title,
        comments = comments,
        likes = likes,
        shareContent = shareContent,
        owner = PostOwnerModel(
            firstName = owner.firstName,
            lastName = owner.lastName,
            profile = owner.profile,
            postDate = owner.postDate
        )
    )
}