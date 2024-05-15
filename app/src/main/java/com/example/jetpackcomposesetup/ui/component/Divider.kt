package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposesetup.ui.theme.JPCSColors

@Composable
fun DefaultDivider(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    thickness: Dp = 1.dp,
    color: Color = Color(0xFFDDDDDD)
) {
    Divider(
        modifier = modifier.padding(paddingValues),
        thickness = thickness,
        color = color,
    )
}

@Composable
fun DashLine(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = JPCSColors.greyText
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}