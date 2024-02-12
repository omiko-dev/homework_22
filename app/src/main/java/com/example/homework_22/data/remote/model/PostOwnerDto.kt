package com.example.homework_22.data.remote.model

import com.squareup.moshi.Json

data class PostOwnerDto (
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    val profile: String?,
    @Json(name = "post_date")
    val postDate: Long
)