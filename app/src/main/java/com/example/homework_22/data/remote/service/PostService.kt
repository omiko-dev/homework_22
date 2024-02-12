package com.example.homework_22.data.remote.service

import com.example.homework_22.data.remote.model.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("25caefd7-7e7e-4178-a86f-e5cfee2d88a0")
    suspend fun getPostList(): Response<List<PostDto>>
}