package com.example.jetpackcomposesetup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.jetpackcomposesetup.ui.login.navigateToLogin
import com.example.jetpackcomposesetup.ui.component.ErrorDialog
import com.example.jetpackcomposesetup.ui.welcome.navigateToWelcome
import com.example.jetpackcomposesetup.ui.welcome.welcomeScreen
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.home.homeScreen
import com.example.jetpackcomposesetup.ui.home.navigateToHome
import com.example.jetpackcomposesetup.ui.login.loginGraph
import com.example.jetpackcomposesetup.ui.login.navigateToLoginCode
import com.example.jetpackcomposesetup.ui.more.moreScreen
import com.example.jetpackcomposesetup.ui.page2.page2Screen
import com.example.jetpackcomposesetup.ui.page3.page3Screen
import com.example.jetpackcomposesetup.ui.welcome.welcomeRoute
import kotlinx.coroutines.flow.StateFlow


@Composable
fun AppNavHost(
	navController: NavHostController,
	onBackClick: () -> Unit,
	modifier: Modifier = Modifier,
	startDestination: String = welcomeRoute,
	isOffline: StateFlow<Boolean>,
	isLoggedIn: Boolean,
) {
	var showNoInternetDialog by remember { mutableStateOf(false) }

	NavHost(
		navController = navController,
		startDestination = startDestination,
		modifier = modifier,
	) {
		// Top level
		homeScreen(
			navigateToNotification = {
				// TODO :
			},
			navigateToContactUs = {

			},
		)
		page2Screen()
		page3Screen()
		welcomeScreen(
			onBackPress = onBackClick,
			navigateToLogin = {
				navController.navigateToLogin()
			},
			navigateToRegister = {
				// TODO :
			},
		)
		moreScreen(
			navigateToLogin = {
				navController.navigateToWelcome()
			},
			navigateToProfile = {},
			navigateToGreeting = {},
			navigateToContactUs = {},
			navigateToFeedback = {},
			navigateToPrivacyPolicy = {},
			navigateToTermsOfService = {},
			onLanguageChange = {},
			onLogOut = {},
		)
		// Children or independent
		loginGraph(
			isOffline = isOffline,
			navigateToLoginCode = {
				navController.navigateToLoginCode()
			},
			onBackClick = onBackClick,
			onSuccess = {
				navController.popBackStack(welcomeRoute, true)
				navController.navigateToHome()
			},
			navigateToContactUs = {
				// TODO :
			}
		)

	}

	ErrorDialog(
		isVisible = showNoInternetDialog,
		title = stringResource(id = R.string.no_connection_title),
		message = stringResource(id = R.string.no_connection_message),
	) {
		showNoInternetDialog = false
	}
}

fun NavHostController.loginOrElse(isLoggedIn: Boolean, block: () -> Unit) {
	if (isLoggedIn) {
		block()
	} else {
		navigateToWelcome()
	}
}
