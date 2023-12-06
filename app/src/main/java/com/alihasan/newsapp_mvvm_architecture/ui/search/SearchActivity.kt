package com.alihasan.newsapp_mvvm_architecture.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivitySearchBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerCountrySelectionActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerSearchActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.CountrySelectionActivityModule
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
        initializeRecyclerView()
        setupObserver()

        handleFetching()
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = articleAdapter
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
                    }
                }
            }
        }
    }

    private fun handleFetching() {
        // Adjust this part based on your search requirements
        val query = "bitcoin" // Replace with the actual search query
        searchViewModel.fetchSearchResults(query)
    }
}
