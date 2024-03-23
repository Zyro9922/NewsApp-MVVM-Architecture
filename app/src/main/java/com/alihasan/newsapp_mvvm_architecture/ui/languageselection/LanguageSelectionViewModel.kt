package com.alihasan.newsapp_mvvm_architecture.ui.languageselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alihasan.newsapp_mvvm_architecture.data.model.Language
import com.alihasan.newsapp_mvvm_architecture.data.repository.LanguageListRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageSelectionViewModel @Inject constructor(
    private val languageListRepository: LanguageListRepository,
) : ViewModel() {

    private val _languageUiState = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)

    val languageUiState: StateFlow<UiState<List<Language>>> = _languageUiState

    init {
        fetchLanguages()
    }

    fun fetchLanguages() {
        viewModelScope.launch {
            languageListRepository.getLanguages()
                .catch { e ->
                    _languageUiState.value = UiState.Error(e.toString())
                }.collect {
                    _languageUiState.value = UiState.Success(it)
                }
        }
    }
}
