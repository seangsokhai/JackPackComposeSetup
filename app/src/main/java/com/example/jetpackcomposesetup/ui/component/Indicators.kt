package com.example.jetpackcomposesetup.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomHorizontalPagerIndicator(
	modifier: Modifier = Modifier,
	pagerState: PagerState,
	pageCount:Int,
	activeColor: Color = Color.White,
	inactiveColor: Color = Color(0x4DFFFFFF),
	activeWidth: Dp = 20.dp,
	inactiveWidth: Dp = 4.dp,
	activeHeight: Dp = 4.dp,
	inactiveHeight: Dp = 4.dp,
	indicatorShape: Shape = ShapeDefaults.Medium.copy(CornerSize(100.dp)),
	space: Dp = 16.dp,
	animationDurationInMillis: Int = 300,
	easing: Easing = LinearOutSlowInEasing
) {

	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(space),
	) {
		for (i in 0 until pageCount) {
			val isSelected = pagerState.currentPage == i

			CustomHorizontalPagerIndicatorView(
				isSelected = isSelected,
				activeColor = activeColor,
				inactiveColor = inactiveColor,
				activeWidth = activeWidth,
				inactiveWidth = inactiveWidth,
				activeHeight = activeHeight,
				inactiveHeight = inactiveHeight,
				animationDurationInMillis = animationDurationInMillis,
				indicatorShape = indicatorShape,
				easing = easing,
			)
		}
	}
}

@Composable
fun CustomHorizontalPagerIndicatorView(
	modifier: Modifier = Modifier,
	isSelected: Boolean,
	activeColor: Color = Color.White,
	inactiveColor: Color = Color(0x4DFFFFFF),
	activeWidth: Dp,
	inactiveWidth: Dp,
	activeHeight: Dp,
	inactiveHeight: Dp,
	animationDurationInMillis: Int,
	indicatorShape: Shape,
	easing: Easing = LinearOutSlowInEasing,
) {
	val height = if (isSelected) activeHeight else inactiveHeight
	val color: Color by animateColorAsState(
		targetValue = if (isSelected) activeColor else inactiveColor,
		label = "",
		animationSpec = tween(
			durationMillis = animationDurationInMillis,
			delayMillis = 50,
			easing = LinearOutSlowInEasing
		)
	)
	val width: Dp by animateDpAsState(
		targetValue = if (isSelected) activeWidth else inactiveWidth,
		label = "",
		animationSpec = tween(
			durationMillis = animationDurationInMillis,
			delayMillis = 50,
			easing = LinearOutSlowInEasing
		)
	)
	
	Box(
		modifier = modifier
			.size(width, height)
			.background(color, indicatorShape)
	)
}

@Composable
fun FullScreenLoadingIndicator() {
	Box(
		Modifier
			.fillMaxSize()
			.offset(y = (-44).dp)
	) {
		CircularProgressIndicator(
			modifier = Modifier.align(Alignment.Center)
		)
	}
}