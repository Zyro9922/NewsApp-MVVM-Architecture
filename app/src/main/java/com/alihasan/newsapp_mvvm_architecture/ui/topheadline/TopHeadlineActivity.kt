package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerTopHeadlineActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineActivityModule
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var topHeadlineRepository: TopHeadlineRepository

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var articleAdapter: TopHeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)
        injectDependencies()
        initializeRecyclerView()
        observeViewModelAndFetchData()
    }

    private fun initializeRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
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
            findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = refreshingState
        }
    }
}