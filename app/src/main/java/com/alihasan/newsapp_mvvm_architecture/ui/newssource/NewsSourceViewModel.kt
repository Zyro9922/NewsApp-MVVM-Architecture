package com.alihasan.newsapp_mvvm_architecture.ui.newssource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel.Source
import com.alihasan.newsapp_mvvm_architecture.data.repository.SourceRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourceViewModel @Inject constructor(
    private val newsRepository: SourceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Source>>> = _uiState

    private val _refreshingState = MutableLiveData<Boolean>()
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    init {
        fetchNewsSources()
    }

    fun fetchNewsSources() {
        _refreshingState.value = true
        viewModelScope.launch {
            try {
                val sourcesResponse = newsRepository.getSources(AppConstant.COUNTRY, AppConstant.LANGUAGE)
                _uiState.value = UiState.Success(sourcesResponse.sources)
            } catch (e: Exception) {
                // Handle the error
                _uiState.value = UiState.Error(e.toString())
            } finally {
                _refreshingState.value = false
            }
        }
    }
}
