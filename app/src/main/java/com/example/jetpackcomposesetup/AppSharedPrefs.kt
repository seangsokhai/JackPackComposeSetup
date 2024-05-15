package com.example.jetpackcomposesetup

import android.content.Context
import android.content.SharedPreferences
import com.example.jetpackcomposesetup.common.SharedPrefsDelegate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AppSharedPrefs(context: Context) : CoroutineScope by MainScope() {
	companion object {
		private const val EXAMPLE_KEY = "example"
		
		private var prefs: AppSharedPrefs? = null
		fun getInstance(context: Context): AppSharedPrefs {
			if (prefs == null) prefs = AppSharedPrefs(context)
			return prefs as AppSharedPrefs
		}
	}
	
	val sharedPrefs: SharedPreferences =
		context.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
	
	var language by SharedPrefsDelegate<String?>() //values: en/km/
	var languageChosen by SharedPrefsDelegate<Boolean?>()
	var onboardingSkip by SharedPrefsDelegate<Boolean?>()
	var sessionToken by SharedPrefsDelegate<String?>()
	var installationId by SharedPrefsDelegate<String?>()
	var fbSmsCode by SharedPrefsDelegate<String?>()
	var fbVerificationId by SharedPrefsDelegate<String?>()
	var fbPhoneNumber by SharedPrefsDelegate<String?>()
	var lastPostLoginCount by SharedPrefsDelegate<String?>()
	var serverUrl by SharedPrefsDelegate<String?>()
	var moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	
	private val _sessionTokenFlow = MutableStateFlow(sessionToken)
	val sessionTokenFlow: Flow<String?> = _sessionTokenFlow

	fun updateSessionToken(token: String?) {
		sessionToken = token
		launch {
			_sessionTokenFlow.emit(token)
		}
	}
}