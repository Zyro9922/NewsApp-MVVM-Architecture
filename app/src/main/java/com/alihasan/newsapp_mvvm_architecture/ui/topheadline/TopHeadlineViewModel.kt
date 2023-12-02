package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.*
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    fun getTopViewModelListOfArticles() : MutableLiveData<List<Article>>{
        return _articles
    }

    private val _refreshingState = MutableLiveData<Boolean>()
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines() {
        _refreshingState.value = true
        viewModelScope.launch {
            try {
                val result = topHeadlineRepository.getTopHeadlines(AppConstant.COUNTRY)
                _articles.value = result.articles.filter {
                    it.title != "[Removed]"
                }
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
