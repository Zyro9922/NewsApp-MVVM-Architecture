package com.alihasan.newsapp_mvvm_architecture.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivitySearchBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerSearchActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.SearchActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var articleAdapter: TopHeadlineAdapter

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        initializeUIElements()
        setupObserver()
    }

    private fun initializeUIElements() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = articleAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            val query = binding.searchView.query.toString().trim()
            if (query.isNotEmpty()) {
                searchViewModel.fetchSearchResults(query)
            }
            binding.swipeRefreshLayout.isRefreshing = true
        }

        // Set the OnQueryTextListener for the SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // Fetch data when the user submits the query
                    searchViewModel.fetchSearchResults(query)
                    // Clear focus to dismiss the keyboard
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // You can perform actions as the user types, if needed
                return true
            }
        })
    }

    private fun injectDependencies() {
        DaggerSearchActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .searchActivityModule(SearchActivityModule(this))
            .build()
            .inject(this)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            articleAdapter.updateData(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            // Handle Error
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_LONG).show()
                        }
                        is UiState.NoData -> {
                            binding.apply {
                                recyclerView.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        searchViewModel.refreshingState.observe(this) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }
}
