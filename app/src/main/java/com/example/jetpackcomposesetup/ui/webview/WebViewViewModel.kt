package com.example.jetpackcomposesetup.ui.webview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, repo: MainRepo
) : ViewModel() {

    private val args = PageArgs(savedStateHandle)
    val screen = args.slug


}