package com.alihasan.newsapp_mvvm_architecture.ui.countryselection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.data.model.Country
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityCountrySelectionBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerCountrySelectionActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.CountrySelectionActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountrySelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var countryListViewModel: CountrySelectionViewModel

    @Inject
    lateinit var countryListAdapter: CountrySelectionAdapter

    private lateinit var binding: ActivityCountrySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = countryListAdapter
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryListViewModel.countryUiState.collect {
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
                                this@CountrySelectionActivity,
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(countryList: List<Country>) {
        countryListAdapter.addCountries(countryList)
        countryListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerCountrySelectionActivityComponent.builder()
            .countrySelectionActivityModule(CountrySelectionActivityModule(this)).build().inject(this)
    }
}