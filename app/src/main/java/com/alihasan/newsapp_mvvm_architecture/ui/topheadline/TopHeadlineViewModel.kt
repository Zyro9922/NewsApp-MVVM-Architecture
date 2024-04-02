package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val _refreshingState = MutableLiveData<Boolean>(false)
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    fun fetchTopHeadlinesByCountry(country: String) {
        viewModelScope.launch {
            _refreshingState.value = true
            try {
                val articles = topHeadlineRepository.getTopHeadlinesByCountry(country).first()
                updateUiStateWithArticles(articles)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _refreshingState.value = false
            }
        }
    }

    fun fetchTopHeadlinesBySourcesId(sourceId: String) {
        viewModelScope.launch {
            _refreshingState.value = true
            try {
                val articles = topHeadlineRepository.getTopHeadlinesBySourceId(sourceId).first()
                updateUiStateWithArticles(articles)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _refreshingState.value = false
            }
        }
    }

    fun fetchTopHeadlinesByLanguage(language: String) {
        viewModelScope.launch {
            _refreshingState.value = true
            try {
                val articles = topHeadlineRepository.getTopHeadlinesByLanguage(language).first()
                updateUiStateWithArticles(articles)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _refreshingState.value = false
            }
        }
    }

    private fun updateUiStateWithArticles(articles: List<Article>) {
        val filteredArticles = articles.filter { it.title != "[Removed]" }
        _uiState.value = UiState.Success(filteredArticles)
    }

    fun fetchSearchResults(query: String) {
        _refreshingState.value = true
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Success(
                    topHeadlineRepository.getEverything(query).articles.filter {
                        it.title != "[Removed]"
                    }
                )
            } catch (e: HttpException) {
                // Handle HTTP exception
                _uiState.value = UiState.Error(e.toString())
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other exceptions
                _uiState.value = UiState.Error(e.toString())
                e.printStackTrace()
            } finally {
                // Ensure that refreshing animation stops in both success and error cases
                _refreshingState.value = false
            }
        }
    }

    private fun handleException(e: Exception) {
        _uiState.value = UiState.Error(e.toString())
        e.printStackTrace()
    }
}