package com.example.jetpackcomposesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesetup.data.repo.MainRepo
import com.example.jetpackcomposesetup.ui.AppLanguage
import com.example.jetpackcomposesetup.ui.asLang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val prefs: AppSharedPrefs,
    val repo: MainRepo,
) : ViewModel() {

    val sessionToken: StateFlow<String?> = prefs.sessionTokenFlow.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = prefs.sessionToken
    )

    val language: Flow<AppLanguage> = flow {
        emit(prefs.language?.asLang() ?: AppLanguage.KHMER)
    }

}