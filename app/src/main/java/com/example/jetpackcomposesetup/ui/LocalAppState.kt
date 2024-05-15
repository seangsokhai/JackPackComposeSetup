package com.example.jetpackcomposesetup.ui

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalAppState: ProvidableCompositionLocal<AppState?> = compositionLocalOf { null }