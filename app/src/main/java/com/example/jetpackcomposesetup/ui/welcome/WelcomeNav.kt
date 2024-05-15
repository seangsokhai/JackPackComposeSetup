package com.example.jetpackcomposesetup.ui.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val welcomeRoute = "welcome"

fun NavController.navigateToWelcome(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(welcomeRoute, navOptions)
}

fun NavGraphBuilder.welcomeScreen(
    onBackPress: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    composable(route = welcomeRoute) {
        WelcomeRoute(
            onBackPress = onBackPress,
            navigateToLogin = navigateToLogin,
            navigateToRegister = navigateToRegister,
        )
    }
}
