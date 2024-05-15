package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme

@Composable
fun VerticalGradientBackground(
    modifier: Modifier = Modifier,
    topColor: Color,
    bottomColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    val currentTopColor by rememberUpdatedState(topColor)
    val currentBottomColor by rememberUpdatedState(bottomColor)
    Box(
        modifier
            .fillMaxWidth()
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    listOf(
                        currentTopColor,
                        currentBottomColor,
                    )
                )
                onDrawBehind {
                    drawRect(gradient)
                }
            }
    ) {
        content()
    }
}

@Composable
fun HorizontalGradientBackground(
    modifier: Modifier = Modifier,
    startColor: Color,
    endColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    val currentStartColor by rememberUpdatedState(startColor)
    val currentEndColor by rememberUpdatedState(endColor)
    Box(
        modifier
            .fillMaxWidth()
            .drawWithCache {
                val gradient = Brush.horizontalGradient(
                    listOf(
                        currentStartColor,
                        currentEndColor,
                    )
                )
                onDrawBehind {
                    drawRect(gradient)
                }
            }
    ) {
        content()
    }
}

@Composable
fun TopShadowGradient(
    modifier: Modifier = Modifier,
    topColor: Color = Color(0xFF5A5A5A),
    bottomColor: Color = Color.Transparent,
    content: @Composable BoxScope.() -> Unit
) {
    VerticalGradientBackground(
        topColor = topColor, bottomColor = bottomColor,
        modifier = modifier,
    ) {
        content()
    }
}

@Composable
fun MainBottomGradient(
    modifier: Modifier = Modifier,
    topColor: Color = Color.Transparent,
    bottomColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable BoxScope.() -> Unit
) {
    VerticalGradientBackground(
        topColor = topColor, bottomColor = bottomColor,
        modifier = modifier,
    ) {
        content()
    }
}

@Composable
fun MoreGradientBackground(
    modifier: Modifier = Modifier,
    topColor: Color,
    bottomColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    val currentTopColor by rememberUpdatedState(topColor)
    val currentBottomColor by rememberUpdatedState(bottomColor)
    Box(
        modifier
            .fillMaxSize()
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    listOf(
                        currentTopColor,
                        currentBottomColor,
                    )
                )
                onDrawBehind {
                    drawRect(gradient)
                }
            }
    ) {
        content()
    }
}

@Preview
@Composable
fun GradientDefault() {
    JetpackComposeSetupTheme {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            TopShadowGradient(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {

            }

            Spacer(modifier = Modifier.size(100.dp))

            MainBottomGradient(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

            }
        }
    }
}