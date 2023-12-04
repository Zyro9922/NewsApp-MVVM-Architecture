package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.util.Log
import androidx.lifecycle.*
import com.alihasan.newsapp_mvvm_architecture.data.local.dao.TopHeadlinesDao
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesEntity
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val topHeadlinesDao: TopHeadlinesDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val _refreshingState = MutableLiveData<Boolean>(false)
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    fun fetchTopHeadlines(country: String? = null, sources: String? = null, category: String? = null) {
        viewModelScope.launch {
            _refreshingState.value = true
            try {
                val minTimestamp =
                    System.currentTimeMillis() - TimeUnit.HOURS.toMillis(AppConstant.TopHeadlineDatabaseUpdateInterval) // 4 hours threshold
                val localData = topHeadlinesDao.getTopHeadlines(minTimestamp)

                val shouldInteractWithLocal = shouldInteractWithLocal(country, sources, category)

                val uiState = if (localData != null && shouldInteractWithLocal) {
                    // Use data from the local database
                    Log.d("TopHeadlineViewModel", "Fetching data from local database")
                    UiState.Success(localData.articles)
                } else {
                    Log.d("TopHeadlineViewModel", "Fetching data from network")
                    val topHeadlineResponse = topHeadlineRepository.getTopHeadlines(country, sources, category)
                    val filteredArticles = topHeadlineResponse.articles.filter {
                        it.title != "[Removed]"
                    }

                    if (shouldInteractWithLocal) {
                        // Save the new data to the local database
                        val topHeadlinesEntity = TopHeadlinesEntity(
                            status = topHeadlineResponse.status,
                            totalResults = topHeadlineResponse.totalResults,
                            articles = filteredArticles,
                            dataLastUpdated = System.currentTimeMillis()
                        )
                        topHeadlinesDao.insertTopHeadlines(topHeadlinesEntity)
                    }

                    // Return the success state with the TopHeadlinesResponse model
                    UiState.Success(filteredArticles)
                }

                _uiState.value = uiState
            } catch (e: HttpException) {
                // Handle HTTP exception
                handleException(e)
            } catch (e: Exception) {
                // Handle other exceptions
                handleException(e)
            } finally {
                // Ensure that refreshing animation stops in both success and error cases
                _refreshingState.value = false
            }
        }
    }

    private fun shouldInteractWithLocal(country: String?, sources: String?, category: String?): Boolean {
        return country == AppConstant.COUNTRY && sources.isNullOrEmpty() && category.isNullOrEmpty()
    }

    private fun handleException(e: Exception) {
        _uiState.value = UiState.Error(e.toString())
        e.printStackTrace()
    }



}
