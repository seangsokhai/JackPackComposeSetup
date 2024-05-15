package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.theme.JPCSColors

@Composable
fun ResIconButton(
	onClick: () -> Unit,
	icon: Int,
	modifier: Modifier = Modifier,
	tint: Color = Color.Unspecified
) {
	IconButton(onClick = onClick, modifier = modifier) {
		Icon(
			painter = painterResource(id = icon),
			contentDescription = null,
			tint = tint
		)
	}
}

@Composable
fun ResIcon(icon: Int, modifier: Modifier = Modifier, tint: Color = Color.Unspecified) {
	Icon(
		painter = painterResource(id = icon),
		contentDescription = null,
		tint = tint,
		modifier = modifier,
	)
}

@Composable
fun ResImage(image: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun BackIcon(modifier: Modifier = Modifier, tint: Color = Color.Unspecified) {
	ResIcon(icon = R.drawable.ic_nav_back, modifier = modifier, tint = tint)
}

@Composable
fun CloseButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	tint: Color = Color.Unspecified
) {
	ResIconButton(onClick = onClick, icon = R.drawable.ic_close, modifier = modifier, tint = tint)
}

@Composable
fun BackButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	tint: Color = Color.Unspecified
) {
	ResIconButton(
		onClick = onClick,
		icon = R.drawable.ic_nav_back,
		modifier = modifier,
		tint = tint
	)
}

@Composable
fun ClickText(
	text: String,
	modifier: Modifier = Modifier,
	textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
	color: Color = MaterialTheme.colorScheme.primary,
	onClick: () -> Unit
) {
	Text(
		text = text,
		style = textStyle,
		color = color,
		modifier = modifier
			.clip(CircleShape)
			.clickable(enabled = true, onClick = onClick)
			.padding(8.dp)
	)
}

@Composable
fun DefaultButton(
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	buttonColor: Color = MaterialTheme.colorScheme.primary,
	disabledButtonColor: Color = Color(0xFFB3B3B3),
	textColor: Color = contentColorFor(backgroundColor = buttonColor),
	minHeight: Dp = 48.dp,
	enabled: Boolean = true,
	shape: Shape = ShapeDefaults.Small,
	fontSize : TextUnit = 15.sp,
	textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
	Button(
		onClick = onClick,
		modifier = modifier.heightIn(min = minHeight),
		shape = shape,
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonColor,
			disabledContainerColor = disabledButtonColor
		),
		enabled = enabled
	) {
		Text(
			text = text,
			color = textColor,
			style = textStyle,
			fontSize = fontSize
		)
	}
}

@Composable
fun PrimaryButton(
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	buttonColor: Color = MaterialTheme.colorScheme.primary,
	textColor: Color = contentColorFor(backgroundColor = buttonColor),
	minHeight: Dp = 48.dp,
	enabled: Boolean = true,
	shape: Shape = ShapeDefaults.Small
) {
	DefaultButton(
		text = text,
		onClick = onClick,
		textColor = textColor,
		buttonColor = buttonColor,
		modifier = modifier,
		minHeight = minHeight,
		enabled = enabled,
		shape = shape
	)
}

@Composable
fun IconButton(
	iconRes: Int,
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	buttonColor: Color = MaterialTheme.colorScheme.primary,
	textColor: Color = contentColorFor(backgroundColor = buttonColor),
	textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
	minHeight: Dp = 48.dp,
	enabled: Boolean = true,
	shape: Shape = ShapeDefaults.Small,
	disabledButtonColor: Color = Color(0xFFB3B3B3),
) {
	Button(
		onClick = onClick,
		modifier = modifier
			.fillMaxWidth()
			.heightIn(min = minHeight),
		shape = shape,
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonColor,
			disabledContainerColor = disabledButtonColor
		),
		enabled = enabled
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
		) {
			ResIcon(icon = iconRes)
			Spacer(modifier = modifier.size(8.dp))
			Text(
				text = text,
				style = textStyle.copy(color = textColor),
			)
		}
	}
}

@Composable
fun SJHIconTextButton(
	onClick: () -> Unit,
	icon: Int,
	modifier: Modifier = Modifier,
	buttonColor: Color = MaterialTheme.colorScheme.primary,
	disabledButtonColor: Color = JPCSColors.greyButton,
	tint: Color = Color.Unspecified,
	text: String,
	textColor: Color = Color.Black
) {
	Button(
		onClick = onClick,
		modifier = modifier
			.fillMaxWidth()
			.heightIn(min = 44.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonColor,
			disabledContainerColor = disabledButtonColor
		),
		shape = ShapeDefaults.Medium,
	) {
		Row(
			horizontalArrangement = Arrangement.Center, 
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				modifier = Modifier.padding(end = 7.dp),
				painter = painterResource(id = icon),
				contentDescription = null,
				tint = tint
			)
			Text(
				text = text,
				style = MaterialTheme.typography.bodySmall,
				fontSize = 13.sp,
				color = textColor,
				fontWeight = FontWeight.Bold,
				overflow = TextOverflow.Ellipsis
			)
		}
	}
}