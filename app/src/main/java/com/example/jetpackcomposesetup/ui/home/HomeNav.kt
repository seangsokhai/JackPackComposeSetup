package com.example.jetpackcomposesetup.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val homeRoute = "home"

fun NavController.navigateToHome(
	navOptions: NavOptions? = navOptions {
		popUpTo(graph.findStartDestination().id) {
			saveState = true
		}
		launchSingleTop = true
		restoreState = true
	}
) {
	this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
	navigateToContactUs: () -> Unit,
	navigateToNotification: () -> Unit,
) {
	composable(route = homeRoute) {
		HomeRoute(
			navigateToContactUs = navigateToContactUs,
			navigateToNotification = navigateToNotification,
		)
	}
}
