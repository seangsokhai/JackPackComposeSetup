package com.example.jetpackcomposesetup

import android.app.Application
import com.example.jetpackcomposesetup.common.initMMKV
import com.example.jetpackcomposesetup.data.repo.MainRepo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

@HiltAndroidApp
class MainApp : Application(), CoroutineScope by MainScope() {

    @Inject
    lateinit var prefs: AppSharedPrefs

    @Inject
    lateinit var mainRepo: MainRepo

    override fun onCreate() {
        super.onCreate()
        initMMKV(this)
    }


}