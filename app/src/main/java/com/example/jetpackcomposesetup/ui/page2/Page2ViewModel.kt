package com.example.jetpackcomposesetup.ui.page2

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Page2ViewModel @Inject constructor(
    private val repo: MainRepo,
    private val prefs: AppSharedPrefs
) : ViewModel() {

}

