package com.example.jetpackcomposesetup.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.jetpackcomposesetup.common.NetworkMonitor
import com.example.jetpackcomposesetup.navigation.AppNavHost
import com.example.jetpackcomposesetup.ui.welcome.welcomeRoute
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposesetup.ui.component.BottomNavigationBar
import com.example.jetpackcomposesetup.ui.component.BottomNavigationBarItem
import com.example.jetpackcomposesetup.ui.component.ErrorDialog
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.navigation.TopLevelDestination
import com.example.jetpackcomposesetup.ui.theme.JPCSColors

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalLayoutApi::class,
)
@Composable
fun App(
	networkMonitor: NetworkMonitor,
	isLoggedIn: Boolean,
	onboardSkip: Boolean,
	appState: AppState = rememberAppState(
		networkMonitor = networkMonitor,
		isLoggedIn = isLoggedIn,
	),
	intent: Intent?,
) {
	Scaffold(
		containerColor = MaterialTheme.colorScheme.background,
		contentColor = MaterialTheme.colorScheme.onBackground,
		contentWindowInsets = WindowInsets(0, 0, 0, 0),
		topBar = {
		
		},
	) { padding ->
		val pushPayload = intent?.extras
		
		LaunchedEffect(isLoggedIn) {
			if (!isLoggedIn && !onboardSkip) {
				appState.navigateToWelcome()
			}
		}
		
		LaunchedEffect(pushPayload) {
			pushPayload?.let {
				println(">>> push payload: $it")
			}
		}
		
		Box(
			Modifier
				.fillMaxSize()
				.windowInsetsPadding(
					WindowInsets.safeDrawing.only(
						WindowInsetsSides.Horizontal
					)
				)
		) {

			CompositionLocalProvider(LocalAppState provides appState) {
				AppNavHost(
					navController = appState.navController,
					onBackClick = appState::onBackClick,
					isOffline = appState.isOffline,
					isLoggedIn = isLoggedIn,
					modifier = Modifier
						.padding(padding)
						.consumeWindowInsets(padding)
				)
			}
			
			NetworkErrorDialog(
				appState = appState,
				paddingBottom = padding.calculateBottomPadding()
			)
		}
	}
}

@Composable
private fun NetworkErrorDialog(appState: AppState, paddingBottom: Dp) {
	val isOffline by appState.isOffline.collectAsStateWithLifecycle()
	var showOfflineDialog by remember { mutableStateOf(false) }
	var didGoOffline by remember { mutableStateOf(false) }
	val shouldReload by remember {
		derivedStateOf {
			!isOffline && didGoOffline
		}
	}
	
	LaunchedEffect(isOffline) {
		if (isOffline) {
			didGoOffline = true
			showOfflineDialog = true
		}
	}
	
	LaunchedEffect(shouldReload) {
		if (shouldReload) {
			didGoOffline = false
			showOfflineDialog = false
			appState.reloadCurrentDestination()
		}
	}
	
	Box(modifier = Modifier.padding(bottom = paddingBottom)) {
		ErrorDialog(
			isVisible = showOfflineDialog,
			title = stringResource(id = R.string.no_connection_title),
			message = stringResource(id = R.string.no_connection_message),
		) {
			showOfflineDialog = false
		}
	}
}

@Composable
private fun AppBottomBar(
	destinations: List<TopLevelDestination>,
	onNavigateToDestination: (TopLevelDestination) -> Unit,
	currentDestination: NavDestination?,
	viewModel: NavBarViewModel = hiltViewModel()
) {
	BottomNavigationBar {
		destinations.forEach { destination ->
			val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
			BottomNavigationBarItem(
				selected = selected,
				onClick = { onNavigateToDestination(destination) },
				icon = {
					val icon = if (selected) {
						destination.selectedIcon
					} else {
						destination.unselectedIcon
					}
					Icon(
						painter = painterResource(id = icon), contentDescription = null
					)
				},
				label = {
					Text(
						text = stringResource(destination.titleTextId),
						color = if (selected) MaterialTheme.colorScheme.primary else JPCSColors.greyText,
						fontSize = 10.sp,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						modifier = Modifier.padding(0.dp)
					)
				},
			)
		}
	}
}

@Composable
internal fun AppStateBottomBar() {
	val appState = LocalAppState.current
	if (appState != null) {
		AppBottomBar(
			destinations = appState.topLevelDestinations,
			onNavigateToDestination = appState::navigateToTopLevelDestination,
			currentDestination = appState.currentDestination
		)
	}
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
	this?.hierarchy?.any {
		it.route?.contains(destination.name, true) ?: false
	} ?: false
