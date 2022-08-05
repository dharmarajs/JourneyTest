package com.example.journeytest.data.remote

import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val postService: PostService
): BaseDataSource() {

    suspend fun getPosts() = getResult { postService.getAllPosts() }

    suspend fun getPostById(postId:Int) = getResult { postService.getPostById(postId) }

    suspend fun getCommentsPostById(postId:Int) = getResult { postService.getCommentsPostById(postId) }
}