package com.example.jetpackcomposesetup.ui.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.coroutines.flow.StateFlow

const val loginGraphRoute = "login_graph"
const val loginRoute = "login"
const val loginCodeRoute = "login_code"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginGraphRoute, navOptions)
}

fun NavController.navigateToLoginCode(navOptions: NavOptions? = null) {
    this.navigate(loginCodeRoute, navOptions)
}

fun NavGraphBuilder.loginGraph(
    navigateToLoginCode: () -> Unit,
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    navigateToContactUs: () -> Unit,
    isOffline : StateFlow<Boolean>
) {
    navigation(
        route = loginGraphRoute,
        startDestination = loginRoute
    ) {
        composable(route = loginRoute) {
            LoginRoute(
                isOffline = isOffline,
                onBackClick = onBackClick,
                navigateToLoginCode = navigateToLoginCode,
                navigateToContactUs = navigateToContactUs,
            )
        }
        composable(route = loginCodeRoute) {
            LoginCodeRoute(
                onBackClick = onBackClick,
                onSuccess = onSuccess
            )
        }
    }
}
