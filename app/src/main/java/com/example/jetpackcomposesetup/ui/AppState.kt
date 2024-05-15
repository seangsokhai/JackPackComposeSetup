package com.example.jetpackcomposesetup.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.jetpackcomposesetup.navigation.TopLevelDestination
import com.example.jetpackcomposesetup.ui.home.homeRoute
import com.example.jetpackcomposesetup.ui.home.navigateToHome
import com.example.jetpackcomposesetup.ui.welcome.navigateToWelcome
import com.example.jetpackcomposesetup.common.NetworkMonitor
import com.example.jetpackcomposesetup.ui.more.moreRoute
import com.example.jetpackcomposesetup.ui.more.navigateToMore
import com.example.jetpackcomposesetup.ui.page2.navigateToPage2
import com.example.jetpackcomposesetup.ui.page3.navigateToPage3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    isLoggedIn: Boolean,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(isLoggedIn, navController, coroutineScope, networkMonitor) {
        AppState(isLoggedIn, navController, coroutineScope, networkMonitor)
    }
}

@Stable
class AppState(
    private val isLoggedIn: Boolean,
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> TopLevelDestination.HOME
            moreRoute -> TopLevelDestination.MORE
            else -> null
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val shouldShowBottomBar: Boolean
        @Composable get() = currentTopLevelDestination != null

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> {
                navController.navigateToHome(topLevelNavOptions)
            }
            TopLevelDestination.PAGE2 -> {
                navController.navigateToPage2(topLevelNavOptions)
            }
            TopLevelDestination.PAGE3 -> {
                navController.navigateToPage3(topLevelNavOptions)
            }
            TopLevelDestination.MORE -> {
                navController.navigateToMore(topLevelNavOptions)
            }
        }
    }

    fun navigateToWelcome() {
        navController.navigateToWelcome(
            navOptions {
                launchSingleTop = true
            }
        )
    }

    fun reloadCurrentDestination() {
        val entry = navController.currentBackStackEntry
        if (entry != null) {
            val currentRoute = entry.routeWithValues()
            navController.popBackStack()
            navController.navigate(currentRoute)
        }
    }

    private fun NavBackStackEntry.routeWithValues(): String {
        val dest = this.destination
        val destArgs = dest.arguments
        val argBundle = this.arguments ?: bundleOf()
        var route = dest.route ?: ""
        destArgs.forEach { entry ->
            val (key, arg) = entry
            val value = arg.type[argBundle, key]
            route = route.replace("{$key}", value.toString())
        }
        return route
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}