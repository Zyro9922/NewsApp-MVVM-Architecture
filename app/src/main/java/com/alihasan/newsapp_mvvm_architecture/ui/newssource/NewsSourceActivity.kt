package com.alihasan.newsapp_mvvm_architecture.ui.newssource

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityNewsSourceBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerNewsSourceActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.NewsSourceActivityModule
import javax.inject.Inject

class NewsSourceActivity : AppCompatActivity() {

    @Inject
    lateinit var newsSourceViewModel: NewsSourceViewModel

    @Inject
    lateinit var newsSourceAdapter: NewsSourceAdapter

    private lateinit var binding: ActivityNewsSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        initializeRecyclerView()
        observeViewModelAndFetchData()
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = newsSourceAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            newsSourceViewModel.fetchNewsSources()
        }
    }

    private fun injectDependencies() {
        DaggerNewsSourceActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .newsSourceActivityModule(NewsSourceActivityModule(this)).build().inject(this)
    }

    private fun observeViewModelAndFetchData() {
        newsSourceViewModel.getNewsSourcesList().observe(this) { newsSources ->
            newsSources?.let {
                newsSourceAdapter.updateData(it)
            }
        }

        newsSourceViewModel.refreshingState.observe(this) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }
}