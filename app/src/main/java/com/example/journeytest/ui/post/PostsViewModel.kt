package com.example.journeytest.ui.post

import androidx.lifecycle.*
import com.example.journeytest.data.entities.Post
import com.example.journeytest.data.repository.PostRepository
import com.example.journeytest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
   private val repository: PostRepository
) : ViewModel() {

    private var _post = repository.getPosts() as MutableLiveData

    val posts: LiveData<Resource<List<Post>>> = _post


    fun getSearchResultForPost(title: String) {
        repository.getSearchResultForPost(title).observeForever(object : Observer<List<Post>> {
            override fun onChanged(post: List<Post>?) {
                if (post == null) {
                    return
                }
                _post.apply {
                    value = Resource.success(post)
                }
            }
        })
    }
}