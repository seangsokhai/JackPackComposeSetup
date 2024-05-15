package com.example.jetpackcomposesetup.ui.page3

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val page3Route = "page3"

fun NavController.navigateToPage3(
	navOptions: NavOptions? = navOptions {
		popUpTo(graph.findStartDestination().id) {
			saveState = true
		}
		launchSingleTop = true
		restoreState = true
	}
) {
	this.navigate(page3Route, navOptions)
}

fun NavGraphBuilder.page3Screen() {
	composable(route = page3Route) {
		Page3Route()
	}
}
