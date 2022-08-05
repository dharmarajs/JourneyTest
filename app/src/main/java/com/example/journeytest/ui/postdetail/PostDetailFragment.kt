package com.example.journeytest.ui.postdetail

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journeytest.R
import com.example.journeytest.databinding.FragmentPostDetailBinding
import com.example.journeytest.utils.Resource
import com.example.journeytest.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private var binding: FragmentPostDetailBinding by autoCleared()
    private val viewModel: PostsDetailViewModel by viewModels()
    private lateinit var adapter: CommentsAdapter
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("postId")?.let { viewModel.start(it) }

        setupRecyclerView()
        setupObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        adapter = CommentsAdapter()
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewComments.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.post.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.tvTitle.text = it.data?.title
                    binding.tvBody.text = it.data?.body
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }

        }

        viewModel.comments.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.searchItems)
        val searchManager =
            activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.queryHint = resources.getString(R.string.search_email)
            searchView?.maxWidth = Integer.MAX_VALUE
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.getSearchResultForComment(it) }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.getSearchResultForComment(it) }
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchItems ->                 // Not implemented here
                return false
            else -> {}
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }
}