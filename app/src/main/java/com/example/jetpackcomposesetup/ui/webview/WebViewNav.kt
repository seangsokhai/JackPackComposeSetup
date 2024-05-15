package com.example.jetpackcomposesetup.ui.webview

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val webViewRoute = "webview"

internal const val pageArg = "page"

internal class PageArgs(val slug: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[pageArg]) as String)
}

fun NavController.navigateToWebView(slug: String, navOptions: NavOptions? = null) {
    this.navigate("$webViewRoute/$slug", navOptions)
}

fun NavGraphBuilder.webViewScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$webViewRoute/{$pageArg}",
        arguments = listOf(navArgument(pageArg) {
            type = NavType.StringType
        })
    ) {
        WebViewRoutes(
            onBackClick = onBackClick
        )
    }
}
