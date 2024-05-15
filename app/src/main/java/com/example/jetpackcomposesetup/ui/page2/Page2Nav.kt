package com.example.jetpackcomposesetup.ui.page2

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val page2Route = "page2"

fun NavController.navigateToPage2(
	navOptions: NavOptions? = navOptions {
		popUpTo(graph.findStartDestination().id) {
			saveState = true
		}
		launchSingleTop = true
		restoreState = true
	}
) {
	this.navigate(page2Route, navOptions)
}

fun NavGraphBuilder.page2Screen() {
	composable(route = page2Route) {
		Page2Route()
	}
}
