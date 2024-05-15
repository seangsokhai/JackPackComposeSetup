package com.example.jetpackcomposesetup.ui.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizedText(
    text: String,
    color: Color = LocalTextStyle.current.color
) {

    var multiplier by remember { mutableFloatStateOf(1f) }

    Text(
        text = text,
        maxLines = 1, // modify to fit your need
        overflow = TextOverflow.Visible,
        style = LocalTextStyle.current.copy(
            fontSize = LocalTextStyle.current.fontSize * multiplier
        ),
        onTextLayout = {
            if (it.hasVisualOverflow) {
                multiplier *= 0.9f
            }
        },
        color = color
    )
}

@Composable
fun BaseMediumText(
    modifier : Modifier = Modifier,
    text : String,
    maxLine : Int = 1,
    color : Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.W400,
    fontSize: TextUnit = 15.sp,
) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = color,
            fontSize = fontSize,
            textAlign = textAlign,
            fontWeight = fontWeight,
        ),
        maxLines = maxLine,
        overflow = TextOverflow.Ellipsis
    )
}