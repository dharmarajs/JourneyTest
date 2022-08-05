package com.example.journeytest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journeytest.data.entities.Comment
import com.example.journeytest.data.entities.Post


@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getAllPosts() : LiveData<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Int): LiveData<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Query("Select * from posts where title like :title")
    fun getSearchResultForPost(title : String) : LiveData<List<Post>>

    @Query("SELECT * FROM comments WHERE postId = :id")
    fun getAllCommentsByPostId(id: Int): LiveData<List<Comment>>

    @Query("SELECT * FROM comments WHERE id = :id")
    fun getCommentById(id: Int): LiveData<Comment?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComment(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)


}
