package com.alihasan.newsapp_mvvm_architecture.ui.newssource

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityNewsSourceBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerNewsSourceActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.NewsSourceActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
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
        setupObserver()
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

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourceViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            newsSourceAdapter.updateData(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            // Handle Error
                            Toast.makeText(this@NewsSourceActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        newsSourceViewModel.refreshingState.observe(this) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }
}