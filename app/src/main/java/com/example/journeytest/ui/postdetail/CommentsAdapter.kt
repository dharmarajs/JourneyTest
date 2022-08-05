package com.example.journeytest.ui.postdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journeytest.data.entities.Comment
import com.example.journeytest.data.entities.Post
import com.example.journeytest.databinding.ItemCommentsBinding
import com.example.journeytest.databinding.ItemPostBinding


class CommentsAdapter() : RecyclerView.Adapter<CommentViewHolder>() {

    private val items = ArrayList<Comment>()

    fun setItems(items: ArrayList<Comment>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding: ItemCommentsBinding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) =
        holder.bind(items[position])
}

class CommentViewHolder(
    private val itemBinding: ItemCommentsBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var post: Comment

    @SuppressLint("SetTextI18n")
    fun bind(item: Comment) {
        this.post = item
        itemBinding.tvEmail.text = item.email
        itemBinding.tvTitle.text = item.name
        itemBinding.tvBody.text = item.body
    }

}