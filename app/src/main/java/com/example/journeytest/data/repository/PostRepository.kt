package com.example.journeytest.data.repository

import com.example.journeytest.data.local.PostDao
import com.example.journeytest.data.remote.PostRemoteDataSource
import com.example.journeytest.utils.performGetOperation
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostDao
) {

    fun getPosts() = performGetOperation(
        databaseQuery = { localDataSource.getAllPosts() },
        networkCall = { remoteDataSource.getPosts() },
        saveCallResult = { localDataSource.insertAllPost(it) }
    )

    fun getPostById(postId:Int) = performGetOperation(
        databaseQuery = { localDataSource.getPostById(postId) },
        networkCall = { remoteDataSource.getPostById(postId) },
        saveCallResult = { localDataSource.insertPost(it) }
    )

    fun getCommentsPostById(postId:Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllCommentsByPostId(postId) },
        networkCall = { remoteDataSource.getCommentsPostById(postId) },
        saveCallResult = { localDataSource.insertAllComment(it) }
    )
}