package com.alihasan.newsapp_mvvm_architecture.ui.newssource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel.Source
import com.alihasan.newsapp_mvvm_architecture.data.repository.SourceRepository
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourceViewModel @Inject constructor(
    private val newsRepository: SourceRepository // Replace with your actual repository
) : ViewModel() {

    private val _newsSources = MutableLiveData<List<Source>>()
    fun getNewsSourcesList(): MutableLiveData<List<Source>> {
        return _newsSources
    }

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
                _newsSources.value = sourcesResponse.sources
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _refreshingState.value = false
            }
        }
    }
}
