package com.example.jetpackcomposesetup.ui

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavBarViewModel @Inject constructor(
    repo: MainRepo,
) : ViewModel() {

}