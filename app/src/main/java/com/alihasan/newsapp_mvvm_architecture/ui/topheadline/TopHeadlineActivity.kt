package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.content.Context
import android.content.Intent
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

    companion object {

        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"
        const val EXTRAS_SOURCE = "EXTRAS_SOURCE"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"

        fun getStartIntentForCountry(context: Context, country: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_COUNTRY, country)
                }
        }

        fun getStartIntentForSource(context: Context, source: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_SOURCE, source)
                }
        }

        fun getStartIntentForLanguage(context: Context, language: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_LANGUAGE, language)
                }
        }
    }

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

        handleFetching()
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = articleAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            handleFetching()
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

    private fun handleFetching(){
        val extrasSource = intent.getStringExtra(EXTRAS_SOURCE)
        val extrasCountry = intent.getStringExtra(EXTRAS_COUNTRY)
        val extrasLanguage = intent.getStringExtra(EXTRAS_LANGUAGE)

        when {
            extrasSource != null -> topHeadlineViewModel.fetchTopHeadlines(extrasSource)
            else -> topHeadlineViewModel.fetchTopHeadlines()
        }
    }
}