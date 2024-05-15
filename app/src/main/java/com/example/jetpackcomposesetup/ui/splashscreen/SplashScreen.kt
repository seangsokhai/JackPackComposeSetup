package com.example.jetpackcomposesetup.ui.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposesetup.R
import kotlinx.coroutines.delay

@Composable
internal fun SplashScreen(
	modifier: Modifier = Modifier,
	splashDelay: Long = 1000L,
	onTimeout: () -> Unit,
) {
	LaunchedEffect(true) {
		delay(splashDelay)
		onTimeout()
	}
	
	Box(
		modifier = modifier
			.fillMaxSize()
			.windowInsetsPadding(WindowInsets.navigationBars),
		contentAlignment = Alignment.Center
	) {
		Box(
			modifier = Modifier.fillMaxSize().background(Color.White)
		)
		
		Image(
			painter = painterResource(id = R.drawable.ic_android),
			contentDescription = null,
			modifier = Modifier
				.size(400.dp)
				.padding(bottom = 210.dp)
		)

		Text(
			text = "Welcome to Jetpack compose App",
			fontSize = 17.sp,
			fontWeight = FontWeight.W500,
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 54.dp)
		)
	}
}