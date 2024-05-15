package com.example.jetpackcomposesetup.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposesetup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
	modifier: Modifier = Modifier,
	colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
	@StringRes titleRes: Int = 0,
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
	navigationIcon: @Composable () -> Unit = {},
	actionIcon: @Composable () -> Unit = {},
) {
	CenterAlignedTopAppBar(
		title = { if (titleRes != 0) Text(text = stringResource(id = titleRes)) },
		navigationIcon = {
			navigationIcon()
		},
		actions = {
			actionIcon()
		},
		colors = colors,
		modifier = modifier
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
	modifier: Modifier = Modifier,
	onActionClick: () -> Unit,
	onContactUsClick: () -> Unit,
	navigationIcon: @Composable () -> Unit = {
		ResIcon(
			R.drawable.ic_android,
			modifier = modifier.size(width = 110.dp, height = 36.dp),
		)
	},
	actionIcon: @Composable () -> Unit = {
		ResIconButton(onClick = onContactUsClick, icon = R.drawable.ic_phone)
		ResIconButton(onClick = onActionClick, icon = R.drawable.ic_nav_notification)
	}
) {
	TopAppBar(
		colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
			containerColor = Color.White
		),
		onActionClick = onActionClick,
		navigationIcon = navigationIcon,
		actionIcon = actionIcon,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloseTopAppBar(
	modifier: Modifier = Modifier,
	@StringRes titleRes: Int = 0,
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
	navigationIcon: @Composable () -> Unit = {
		CloseButton(onClick = onNavigationClick)
	},
	actionIcon: @Composable () -> Unit = {},
) {
	CenterAlignedTopAppBar(title = {
		if (titleRes != 0) {
			Text(text = stringResource(id = titleRes))
		}
	}, navigationIcon = {
		navigationIcon()
	}, actions = {
		actionIcon()
	}, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
		modifier = modifier
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopAppBar(
	modifier: Modifier = Modifier,
	@StringRes titleRes: Int = 0,
	colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
	navigationIcon: @Composable () -> Unit = {
		BackButton(onClick = onNavigationClick)
	},
	actionIcon: @Composable () -> Unit = {},
) {
	CenterAlignedTopAppBar(
		title = {
			if (titleRes != 0) {
				Text(
					text = stringResource(id = titleRes),
					style = MaterialTheme.typography.titleLarge.copy(
						fontWeight = FontWeight.Bold, fontSize = 20.sp
					)
				)
			}
		},
		navigationIcon = {
			navigationIcon()
		},
		actions = {
			actionIcon()
		},
		colors = colors,
		modifier = modifier
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeTopAppBar(
	modifier: Modifier = Modifier,
	onActionClick: () -> Unit = {},
	onCloseClick: () -> Unit = {},
	navigationIcon: @Composable () -> Unit = {},
	actionIcon: @Composable () -> Unit = {},
) {
	CenterAlignedTopAppBar(
		title = {
			ResIcon(
				R.drawable.ic_android,
				modifier = modifier.size(width = 410.dp, height = 66.dp),
			)
		},
		colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
			containerColor = Color.Transparent
		),
		modifier = modifier
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleToolbar(
	modifier: Modifier = Modifier,
	@StringRes titleRes: Int = 0,
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
	scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
	color: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(scrolledContainerColor = Color.White),
	navigationIcon: @Composable () -> Unit = {
		BackButton(onClick = onNavigationClick)
	},
	actionIcon: @Composable () -> Unit = {},
) {
	MediumTopAppBar(
		modifier = modifier,
		title = {
			if (titleRes != 0) {
				Text(
					text = stringResource(id = titleRes),
					style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700, fontSize = 26.sp),
				)
			}
		},
		navigationIcon = navigationIcon,
		actions = { actionIcon() },
		colors = color,
		scrollBehavior = scrollBehavior
	)
}