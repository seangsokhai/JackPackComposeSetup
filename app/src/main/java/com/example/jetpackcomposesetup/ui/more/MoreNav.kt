package com.example.jetpackcomposesetup.ui.more

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val moreRoute = "more"

fun NavController.navigateToMore(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
) {
    this.navigate(moreRoute, navOptions)
}

fun NavGraphBuilder.moreScreen(
    navigateToLogin: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToGreeting: () -> Unit,
    navigateToContactUs: () -> Unit,
    navigateToFeedback: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToTermsOfService: () -> Unit,
    onLanguageChange: () -> Unit,
    onLogOut: () -> Unit,
) {
    composable(route = moreRoute) {
        MoreRoute(
            navigateToLogin = navigateToLogin,
            navigateToProfile = navigateToProfile,
            navigateToGreeting = navigateToGreeting,
            navigateToContactUs = navigateToContactUs,
            navigateToFeedback = navigateToFeedback,
            navigateToPrivacyPolicy = navigateToPrivacyPolicy,
            navigateToTermsOfService = navigateToTermsOfService,
            onLanguageChange = onLanguageChange,
            onLogOut = onLogOut,
        )
    }
}
