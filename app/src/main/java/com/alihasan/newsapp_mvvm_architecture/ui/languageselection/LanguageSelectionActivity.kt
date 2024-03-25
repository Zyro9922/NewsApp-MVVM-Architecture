package com.alihasan.newsapp_mvvm_architecture.ui.languageselection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.data.model.Language
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityLanguageSelectionBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.ActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageSelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var languageListViewModel: LanguageSelectionViewModel

    @Inject
    lateinit var languageListAdapter: LanguageSelectionAdapter

    private lateinit var binding: ActivityLanguageSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = languageListAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            languageListViewModel.fetchLanguages()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageListViewModel.languageUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.apply {
                                recyclerView.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(
                                this@LanguageSelectionActivity,
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        languageListViewModel.refreshingState.observe(this) { refreshingState ->
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }

    private fun renderList(languageList: List<Language>) {
        languageListAdapter.setLanguages(languageList)
        languageListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}