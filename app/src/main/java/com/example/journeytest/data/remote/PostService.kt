package com.example.journeytest.data.remote

import com.example.journeytest.data.entities.Comment
import com.example.journeytest.data.entities.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    @GET("posts")
    suspend fun getAllPosts(): Response<ArrayList<Post>>

    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Response<Post>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsPostById(@Path("postId") postId: Int): Response<ArrayList<Comment>>
}