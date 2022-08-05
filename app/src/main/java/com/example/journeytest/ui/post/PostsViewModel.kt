package com.example.journeytest.ui.post

import androidx.lifecycle.ViewModel
import com.example.journeytest.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    val posts = repository.getPosts()
}