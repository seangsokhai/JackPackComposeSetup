@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposesetup.ui.page2

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.ui.AppStateBottomBar
import java.util.*

@Composable
internal fun Page2Route(
	modifier: Modifier = Modifier,
	viewModel: Page2ViewModel = hiltViewModel()
) {

	Page2Screen(
		modifier = modifier,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Page2Screen(
	modifier: Modifier = Modifier,
) {
	Scaffold(
		topBar = {},
		bottomBar = {
			AppStateBottomBar()
		},
	) { padding ->

		Column(
			modifier = modifier
				.fillMaxSize()
				.background(Color(0xFFF6F8FA))
				.padding(top = padding.calculateTopPadding())
				.padding(bottom = padding.calculateBottomPadding())
				.padding(bottom = 16.dp),
		) {
			Text(
				text = "Page 2",
				textAlign = TextAlign.Center,
				modifier = modifier.fillMaxSize()
			)
		}
	}
}


