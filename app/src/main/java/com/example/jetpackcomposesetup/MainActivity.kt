package com.example.jetpackcomposesetup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposesetup.common.NetworkMonitor
import com.example.jetpackcomposesetup.ui.App
import com.example.jetpackcomposesetup.ui.asLang
import com.example.jetpackcomposesetup.ui.splashscreen.SplashScreen
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var prefs: AppSharedPrefs

    private val viewModel: MainViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val systemAppLang = AppCompatDelegate.getApplicationLocales()[0]?.language
        val lang = systemAppLang ?: AppSharedPrefs.getInstance(newBase).language ?: "km" //en/km
        val config = newBase.resources.configuration.apply {
            setLocale(Locale(lang))
        }
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val context = LocalContext.current
            val language by viewModel.language.collectAsState(initial = prefs.language?.asLang())
            var splashShown by remember {
                mutableStateOf(false)
            }

            val permissionOpenDialog = remember { mutableStateOf(false) }
            val rationalPermissionOpenDialog = remember { mutableStateOf(false) }
            val hasNotificationPermission = remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                } else mutableStateOf(true)
            }

            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (!isGranted) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                            rationalPermissionOpenDialog.value = true
                        } else {
                            permissionOpenDialog.value = true
                        }
                    } else {
                        hasNotificationPermission.value = isGranted
                    }
                }

            LaunchedEffect(hasNotificationPermission.value) {
                if (!hasNotificationPermission.value) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            JetpackComposeSetupTheme(language = language) {
                val sessionToken by viewModel.sessionToken.collectAsStateWithLifecycle()

                if (!splashShown) {
                    LaunchedEffect(Unit) {
                        window.statusBarColor = Color.Transparent.toArgb()
                        WindowCompat.getInsetsController(
                            window,
                            window.decorView
                        ).isAppearanceLightStatusBars = true
                    }
                    SplashScreen {
                        splashShown = true
                    }
                } else {
                    App(
                        networkMonitor = networkMonitor,
                        isLoggedIn = sessionToken != null,
                        onboardSkip = prefs.onboardingSkip == true,
                        intent = intent,
                    )
                }
            }
        }
    }
}
