package com.example.journeytest.ui.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journeytest.data.entities.Post
import com.example.journeytest.databinding.ItemPostBinding


class PostAdapter(private val listener: PostItemListener) : RecyclerView.Adapter<PostViewHolder>() {

    interface PostItemListener {
        fun onClickedPost(postId: Int)
    }

    private val items = ArrayList<Post>()

    fun setItems(items: ArrayList<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: ItemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(items[position])
}

class PostViewHolder(
    private val itemBinding: ItemPostBinding,
    private val listener: PostAdapter.PostItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var post: Post

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Post) {
        this.post = item
        itemBinding.tvTitle.text = item.title
        itemBinding.tvBody.text = item.body
    }

    override fun onClick(v: View?) {
        listener.onClickedPost(post.id)
    }
}