package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerTopHeadlineActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var articleAdapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        initializeRecyclerView()
        setupObserver()
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = articleAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            topHeadlineViewModel.fetchTopHeadlines()
        }
    }

    private fun injectDependencies() {
        DaggerTopHeadlineActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .topHeadlineActivityModule(TopHeadlineActivityModule(this)).build().inject(this)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            articleAdapter.updateData(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        topHeadlineViewModel.refreshingState.observe(this) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }
}