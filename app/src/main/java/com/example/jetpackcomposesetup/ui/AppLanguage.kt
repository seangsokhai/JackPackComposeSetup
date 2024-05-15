package com.example.jetpackcomposesetup.ui

import com.example.jetpackcomposesetup.R


enum class AppLanguage(
    val language: String, val icon: Int, val label: Int
) {
    KHMER(
        language = "km", icon = R.drawable.ic_khmer_flag, label = R.string.language_khmer
    ),
    ENGLISH(
        language = "en", icon = R.drawable.ic_english_flag, label = R.string.language_english
    ),
}

fun String.asLang(): AppLanguage {
    return AppLanguage.values().find { it.language == this } ?: AppLanguage.KHMER
}