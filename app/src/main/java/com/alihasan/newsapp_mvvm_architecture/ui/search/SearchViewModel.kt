package com.alihasan.newsapp_mvvm_architecture.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val _refreshingState = MutableLiveData<Boolean>(false)
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    init {
        fetchSearchResults("bitcoin")
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
}
