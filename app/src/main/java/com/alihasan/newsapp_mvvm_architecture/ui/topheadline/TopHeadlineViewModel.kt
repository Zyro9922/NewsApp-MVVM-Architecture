package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.Article
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _refreshingState = MutableLiveData<Boolean>()
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    fun fetchTopHeadlines(country: String) {
        _refreshingState.value = true
        viewModelScope.launch {
            try {
                val result = topHeadlineRepository.getTopHeadlines(country)
                _articles.value = result.articles
            } catch (e: HttpException) {
                // Handle HTTP exception
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other exceptions
                e.printStackTrace()
            } finally {
                // Ensure that refreshing animation stops in both success and error cases
                _refreshingState.value = false
            }
        }
    }


}
