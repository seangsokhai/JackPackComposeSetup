package com.example.jetpackcomposesetup.ui.login

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val repo: MainRepo, private val prefs: AppSharedPrefs
) : ViewModel() {
	companion object {
		private const val TAG = ">>>"
	}
	private fun String.asFormattedPhoneNumber(): String {
		return if (!this.startsWith("+855")) {
			"+855" + this.trimStart('0')
		} else {
			this
		}
	}

}