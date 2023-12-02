package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerTopHeadlineActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineActivityModule
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var topHeadlineRepository: TopHeadlineRepository

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
        observeViewModelAndFetchData()
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

    private fun observeViewModelAndFetchData() {
        topHeadlineViewModel.getTopViewModelListOfArticles().observe(this) { articles ->
            articles?.let {
                articleAdapter.updateData(it)
            }
        }

        topHeadlineViewModel.refreshingState.observe(this) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }
}