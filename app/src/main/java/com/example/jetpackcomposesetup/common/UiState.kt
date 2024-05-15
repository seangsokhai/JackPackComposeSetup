package com.example.jetpackcomposesetup.common

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable? = null) : UiState<Nothing>
    object Loading : UiState<Nothing>
}