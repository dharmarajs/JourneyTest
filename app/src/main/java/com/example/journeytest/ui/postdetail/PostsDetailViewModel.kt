package com.example.journeytest.ui.postdetail

import androidx.lifecycle.*
import com.example.journeytest.data.entities.Comment
import com.example.journeytest.data.entities.Post
import com.example.journeytest.data.repository.PostRepository
import com.example.journeytest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsDetailViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()

    private val _post = _id.switchMap { id ->
        repository.getPostById(id)
    }
    val post: LiveData<Resource<Post>> = _post

    private val _comments = _id.switchMap { id ->
        repository.getCommentsPostById(id)
    } as MutableLiveData

    val comments: LiveData<Resource<List<Comment>>> = _comments

    fun start(id: Int) {
        _id.value = id
    }

    fun getSearchResultForComment(email: String) {
        repository.getSearchResultForComment(email).observeForever(object : Observer<List<Comment>> {
            override fun onChanged(comments: List<Comment>?) {
                if (comments == null) {
                    return
                }
                _comments.apply {
                    value = Resource.success(comments)
                }
            }
        })
    }
}