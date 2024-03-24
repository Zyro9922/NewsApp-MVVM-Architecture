package com.alihasan.newsapp_mvvm_architecture.ui.countryselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.Country
import com.alihasan.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountrySelectionViewModel @Inject constructor(
    private val countryListRepository: CountryListRepository) : ViewModel() {

    private val _countryUiState = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)
    val countryUiState: StateFlow<UiState<List<Country>>> = _countryUiState

    private val _refreshingState = MutableLiveData<Boolean>(false)
    val refreshingState: LiveData<Boolean> get() = _refreshingState

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _refreshingState.value = true
            try{
                countryListRepository.getCountries()
                    .catch { e ->
                        _countryUiState.value = UiState.Error(e.toString())
                    }.collect {
                        _countryUiState.value = UiState.Success(it)
                    }
            } finally {
                _refreshingState.value = false
            }
        }
    }
}