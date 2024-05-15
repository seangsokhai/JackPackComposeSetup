package com.example.jetpackcomposesetup.ui.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.common.clearMMKVCache
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoreViewModel @Inject constructor(
    private val repo: MainRepo,
    private val prefs: AppSharedPrefs
) : ViewModel() {

    // TODO : when we has logout Api
    /*fun logOut(onDone: () -> Unit) {
        viewModelScope.launch {
            repo.logout()
                .asResult()
                .collectLatest {
                    clearMMKVCache()
                    onDone()
                }
        }
    }*/
    fun saveUserLanguage(lang: String) { prefs.language = lang }
    fun getUserLanguage(): String = prefs.language ?: "km"

    fun clearCache(){
        viewModelScope.launch {
            clearMMKVCache()
        }
    }
}