package com.example.jetpackcomposesetup.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.common.asResult
import com.example.jetpackcomposesetup.common.Result
import com.example.jetpackcomposesetup.common.clearMMKVCache
import com.example.jetpackcomposesetup.data.model.CharacterDto
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repo: MainRepo,
    private val prefs: AppSharedPrefs
) : ViewModel() {
    fun clearCache(){
        viewModelScope.launch {
            clearMMKVCache()
        }
    }

    val characterUiState: StateFlow<CharacterUiState> = repo
        .getCharacters()
        .retry(2) { it is HttpException }
        .asResult()
        .map {
            when (it) {
                is Result.Error -> CharacterUiState.Error
                is Result.Loading -> CharacterUiState.Loading
                is Result.Success -> CharacterUiState.Success(it.data)
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = CharacterUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}

sealed interface CharacterUiState {
    object Loading : CharacterUiState
    object Error : CharacterUiState
    data class Success(val items: List<CharacterDto>) : CharacterUiState
}