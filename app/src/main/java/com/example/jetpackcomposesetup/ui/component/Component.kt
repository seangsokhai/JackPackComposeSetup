package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.component.ResIcon
import com.example.jetpackcomposesetup.ui.theme.JPCSColors


@Composable
fun ErrorView(
	modifier: Modifier = Modifier,
	title: String? = stringResource(id = R.string.error_something_is_wrong),
	message: String,
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.fillMaxSize(),
	) {
		ResIcon(icon = R.drawable.ic_failed)
		if (title != null) {
			Text(
				text = title,
				style = MaterialTheme.typography.displaySmall,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(top = 8.dp)
			)
		}
		Text(
			text = message,
			style = MaterialTheme.typography.bodyMedium,
			color = Color(0xFF777F89),
			textAlign = TextAlign.Center,
			modifier = Modifier.padding(top = 8.dp)
		)
	}
}

//TODO: update empty state UI
@Composable
fun EmptyView(
	modifier: Modifier = Modifier,
	icon: Int = -1,
	title: String? = null,
	subtitle: String? = null,
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.fillMaxSize(),
	) {
		if (icon != -1) {
			ResIcon(icon = icon)
		}
		
		if (title != null) {
			Text(
				text = title,
				style = MaterialTheme.typography.displaySmall,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(top = 8.dp)
			)
		}
		
		if (subtitle != null) {
			Text(
				text = subtitle,
				style = MaterialTheme.typography.bodyMedium,
				color = Color(0xFF777F89),
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(top = 8.dp)
			)
		}
	}
}

@Composable
fun CustomAsyncImage(
	modifier: Modifier = Modifier,
	url: String?,
	contentScale: ContentScale = ContentScale.Crop,
	alpha: Float = DefaultAlpha,
	contentDescription: String? = null,
	placeholder: Painter? = null,
	error: Painter? = null,
	fallback: Painter? = error,
	onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
	onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
	onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
	alignment: Alignment = Alignment.Center,
	colorFilter: ColorFilter? = null,
	filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
	AsyncImage(
		modifier = modifier,
		model = url,
		contentDescription = contentDescription,
		contentScale = contentScale,
		alignment = alignment,
		colorFilter = colorFilter,
		filterQuality = filterQuality,
		alpha = alpha,
		placeholder = placeholder,
		error = error ?: painterResource(id = R.drawable.ic_warning),
		fallback = fallback,
		onLoading = onLoading ?: {
			//unsure what to do here :(
		},
		onError = onError ?: {
			//unsure what to do here :(
		},
		onSuccess = onSuccess ?: {
			//unsure what to do here :(
		}
	)
}

@Composable
fun EmptyScreenAnimation(
	modifier: Modifier = Modifier,
	title: String,
	subTitle: String,
	icon : Int,
	cacheKey : String,
) {
	val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(icon), cacheKey = cacheKey)
	val progress by animateLottieCompositionAsState(composition, restartOnPlay = true)

	Column(
		modifier = modifier
			.fillMaxSize()
			.background(JPCSColors.greyContainer),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Top
	) {
		Spacer(modifier = modifier.size(20.dp))
		LottieAnimation(
			composition = composition,
			progress = { progress },
			modifier = modifier.size(320.dp, 320.dp),
		)
		Text(
			text = title,
			style = MaterialTheme.typography.titleLarge.copy(
				fontWeight = FontWeight.Bold
			)
		)
		Spacer(modifier = modifier.size(13.dp))
		Text(
			text = subTitle,
			style = MaterialTheme.typography.bodyMedium,
			color = JPCSColors.greyText,
			textAlign = TextAlign.Center,
			modifier = modifier.padding(horizontal = 30.dp)
		)
	}
}