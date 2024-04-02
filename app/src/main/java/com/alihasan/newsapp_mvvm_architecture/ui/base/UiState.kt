package com.alihasan.newsapp_mvvm_architecture.ui.base
sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data object NoData : UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    data object Loading : UiState<Nothing>

}