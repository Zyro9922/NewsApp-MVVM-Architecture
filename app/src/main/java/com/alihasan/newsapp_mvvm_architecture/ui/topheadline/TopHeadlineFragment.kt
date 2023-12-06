package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.databinding.FragmentTopHeadlineBinding
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerTopHeadlineFragmentComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineFragmentModule
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity.Companion.EXTRAS_COUNTRY
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity.Companion.EXTRAS_LANGUAGE
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity.Companion.EXTRAS_SOURCE
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineFragment : Fragment() {

    private var _binding: FragmentTopHeadlineBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var articleAdapter: TopHeadlineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initializeRecyclerView()
        setupObserver()
        handleFetchingFromTopHeadlineActivity()
    }

    private fun injectDependencies() {
        DaggerTopHeadlineFragmentComponent.builder()
            .applicationComponent((requireActivity().application as NewsApplication).applicationComponent)
            .topHeadlineFragmentModule(TopHeadlineFragmentModule(this)).build().inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = articleAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            handleFetchingFromTopHeadlineActivity()
        }
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
                            Toast.makeText(
                                requireContext(),
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        topHeadlineViewModel.refreshingState.observe(viewLifecycleOwner) { refreshingState ->
            // Update the refreshing state of SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = refreshingState
        }
    }

    private fun handleFetchingFromTopHeadlineActivity() {
        val country = arguments?.getString(EXTRAS_COUNTRY)
        val source = arguments?.getString(EXTRAS_SOURCE)
        val language = arguments?.getString(EXTRAS_LANGUAGE)

        topHeadlineViewModel.fetchTopHeadlines(country, source, language)
    }

    // Search function to be called from SearchActivity
    fun search(query: String) {
        // Optionally, you can perform UI updates or loading indicators for the search process
        topHeadlineViewModel.fetchSearchResults(query)
    }

    companion object {
        fun newInstance(country: String? = null, source: String? = null, language: String? = null): TopHeadlineFragment {
            return TopHeadlineFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRAS_COUNTRY, country)
                    putString(EXTRAS_SOURCE, source)
                    putString(EXTRAS_LANGUAGE, language)
                }
            }
        }
    }
}
