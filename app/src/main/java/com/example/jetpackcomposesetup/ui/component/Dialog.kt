package com.example.jetpackcomposesetup.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.theme.JPCSColors


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseDialog(
	isVisible: Boolean, onDismiss: () -> Unit, content: @Composable () -> Unit
) {
	val scope = rememberCoroutineScope()
	val sheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		skipHalfExpanded = true,
		confirmValueChange = { true }
	)
	
	ModalBottomSheetLayout(sheetState = sheetState,
		scrimColor = Color.Black.copy(alpha = 0.4f),
		sheetContentColor = Color.Transparent,
		sheetBackgroundColor = Color.Transparent,
		sheetElevation = 0.dp,
		sheetContent = {
			content()
			BackHandler(sheetState.currentValue == ModalBottomSheetValue.Expanded) {
				scope.launch {
					onDismiss()
					sheetState.hide()
				}
			}
		}) {}
	
	LaunchedEffect(isVisible) {
		if (isVisible) {
			sheetState.show()
		} else {
			sheetState.hide()
		}
		
	}
	
	LaunchedEffect(isVisible, sheetState.currentValue, sheetState.targetValue) {
		if (isVisible && sheetState.currentValue == sheetState.targetValue &&
			sheetState.currentValue == ModalBottomSheetValue.Hidden
		) {
			onDismiss()
		}
	}
}

@Composable
fun ContentDialog(
	isVisible: Boolean,
	onDismiss: () -> Unit,
	modifier: Modifier = Modifier,
	shape: RoundedCornerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
	beforeContent: @Composable ColumnScope.() -> Unit = {},
	afterContent: @Composable ColumnScope.() -> Unit = {},
	content: @Composable ColumnScope.() -> Unit
) {
	BaseDialog(
		isVisible = isVisible, onDismiss = onDismiss
	) {
		Box(
			modifier = modifier
				.windowInsetsPadding(WindowInsets.navigationBars)
				.fillMaxWidth()
				.clip(shape = shape)
				.background(MaterialTheme.colorScheme.onPrimary)
		) {
			Column(modifier.fillMaxWidth()) {
				beforeContent()
				content()
				afterContent()
			}
		}
	}
}

@Composable
fun TitleDialog(
	isVisible: Boolean,
	onClose: () -> Unit,
	title: String,
	modifier: Modifier = Modifier,
	style: TextStyle = MaterialTheme.typography.titleSmall.copy(
		fontSize = 20.sp, fontWeight = FontWeight.Bold
	),
	content: @Composable ColumnScope.() -> Unit
) {
	
	ContentDialog(isVisible = isVisible, onDismiss = onClose, beforeContent = {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = modifier
				.fillMaxWidth()
				.padding(start = 16.dp, top = 16.dp)
		) {
			Text(
				title,
				style = style,
			)
			Spacer(modifier = Modifier.weight(1f))
			ResIconButton(
				onClick = onClose, icon = R.drawable.ic_close
			)
			Spacer(modifier = Modifier.size(8.dp))
		}
	}) {
		content()
	}
}

@Composable
fun MessageDialog(
	isVisible: Boolean,
	title: String,
	message: String,
	modifier: Modifier = Modifier,
	//TODO fix this string
	actionLabelRes: Int = android.R.string.ok,
	onAction: () -> Unit
) {
	
	ContentDialog(
		isVisible = isVisible,
		onDismiss = onAction,
	) {
		Column(
			modifier = modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
				modifier = modifier.fillMaxWidth(),
				textAlign = TextAlign.Center
			)
			Spacer(modifier = Modifier.size(8.dp))
			Text(
				text = message,
				style = MaterialTheme.typography.bodyMedium.copy(
					fontSize = 15.sp, color = MaterialTheme.colorScheme.onSecondaryContainer
				),
				modifier = modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
				textAlign = TextAlign.Center
			)
			Spacer(modifier = Modifier.size(16.dp))
			Button(
				onClick = onAction, modifier = modifier.fillMaxWidth()
			) {
				Text(text = stringResource(id = actionLabelRes))
			}
		}
	}
}

@Composable
fun LoadingDialog(
	isLoading: Boolean = false
) {
	AnimatedVisibility(
		visible = isLoading,
		enter = fadeIn(),
		exit = fadeOut(),
	) {
		Box(modifier = Modifier
			.fillMaxSize()
			.clickable(enabled = false) { }
			.background(MaterialTheme.colorScheme.scrim.copy(alpha = .4f)),
			contentAlignment = Alignment.Center) {
			CircularProgressIndicator()
		}
	}
}

@Composable
fun ErrorDialog(
	isVisible: Boolean,
	title: String,
	message: String,
	modifier: Modifier = Modifier,
	onAction: () -> Unit
) {
	
	ContentDialog(
		isVisible = isVisible,
		onDismiss = onAction,
		modifier = modifier
	) {
		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(
				modifier = Modifier
					.padding(top = 12.dp, bottom = 27.dp)
					.size(36.dp, 5.dp)
					.clip(ShapeDefaults.Small)
					.background(Color(0x4C3C3C43))
			)
			ResIcon(icon = R.drawable.ic_failed)
			Spacer(modifier = Modifier.size(12.dp))
			Text(
				text = title,
				style = MaterialTheme.typography.displaySmall,
				textAlign = TextAlign.Center
			)
			Spacer(modifier = Modifier.size(10.dp))
			Text(
				text = "$message",
				style = MaterialTheme.typography.bodyMedium,
				color = Color(0xFF777F89),
				textAlign = TextAlign.Center,
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 16.dp)
			)
			Spacer(modifier = Modifier.size(70.dp))
		}
	}
}

@Composable
fun ComingSoonDialog(
	isVisible: Boolean,
	modifier: Modifier = Modifier,
	onAction: () -> Unit
) {
	ContentDialog(
		isVisible = isVisible,
		onDismiss = onAction,
		modifier = modifier
	) {
		Column(
			modifier = modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = "Coming Soon",
				style = MaterialTheme.typography.displaySmall.copy(
					fontSize = 22.sp,
					fontWeight = FontWeight.W700
				),
				textAlign = TextAlign.Center,
			)
			Spacer(modifier = modifier.size(10.dp))
			Text(
				text = "The new feature is on the way. Stay tuned!",
				style = MaterialTheme.typography.bodyMedium,
				color = Color(0xFF777F89),
				textAlign = TextAlign.Center
			)
			Spacer(modifier = modifier.size(16.dp))
			PrimaryButton(
				text = "Got it",
				onClick = onAction,
				modifier = modifier.fillMaxWidth(),
			)
		}
	}
}

@Composable
fun PhotoDialog(
	isVisible: Boolean,
	modifier: Modifier = Modifier,
	labelCancel: Int = R.string.common_cancel,
	labelLibrary: Int = R.string.photo_library,
	labelCamera: Int = R.string.camera,
	onAction: (String) -> Unit,
	isChooseFile: Boolean = false,
	onChooseFileClick: () -> Unit = {},
) {
	
	BaseDialog(
		isVisible = isVisible,
		onDismiss = { onAction("") }
	) {
		Box(
			modifier = Modifier
				.padding(16.dp)
				.padding(bottom = 32.dp)
		) {
			Column(
				modifier = modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.clip(RoundedCornerShape(size = 13.dp))
						.background(Color(0xF2F5F5F5))
				) {
					BtnActionRow(
						onAction = { },
						labelName = labelLibrary
					)
					Divider(thickness = 1.dp, color = JPCSColors.greyText, modifier = modifier)
					BtnActionRow(
						onAction = {},
						labelName = labelCamera
					)
					if (isChooseFile) {
						Divider(thickness = 1.dp, color = JPCSColors.greyText, modifier = modifier)
						BtnActionRow(
							onAction = { onChooseFileClick() },
							labelName = R.string.choose_file
						)
					}
				}
				Spacer(modifier = modifier.height(10.dp))
				BtnActionRow(
					modifier = modifier.background(Color.White, RoundedCornerShape(size = 13.dp)),
					onAction = { onAction("") },
					labelName = labelCancel
				)
				Spacer(modifier = modifier.height(16.dp))
			}
		}
	}
}

@Composable
fun BtnActionRow(
	modifier: Modifier = Modifier,
	onAction: () -> Unit,
	labelName: Int,
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.fillMaxWidth()
			.height(60.dp)
			.clickable { onAction() },
	) {
		Text(
			stringResource(id = labelName),
			style = MaterialTheme.typography.displaySmall.copy(
				color = JPCSColors.blue,
				fontSize = 20.sp,
				fontWeight = FontWeight.W400,
				textAlign = TextAlign.Left,
			),
		)
	}
}

@Composable
fun AlertDialog(
	onConfirmButton: () -> Unit,
	onDismissRequest: () -> Unit,
	title: String,
	text: String,
	labelPositive: String = stringResource(id = R.string.app_settings),
	labelNegative: String = stringResource(id = R.string.not_now),
) {
	AlertDialog(
		onDismissRequest = onDismissRequest,
		title = {
			Text(
				text = title,
				style = MaterialTheme.typography.displaySmall.copy(
					color = colorResource(id = R.color.black),
					fontSize = 17.sp,
					fontWeight = FontWeight.W600,
				),
			)
		},
		text = {
			Text(
				text = text,
				style = MaterialTheme.typography.displaySmall.copy(
					color = JPCSColors.greyText,
					fontSize = 14.sp,
					fontWeight = FontWeight.W400,
					lineHeight = 20.sp
				),
			)
		},
		confirmButton = {
			TextButton(
				onClick = onConfirmButton,
			) {
				Text(
					text = labelPositive,
					color = JPCSColors.greyText
				)
			}
		},
		dismissButton = {
			TextButton(
				onClick = onDismissRequest,
			) {
				Text(
					text = labelNegative,
					color = JPCSColors.blue
				)
			}
		},
	)
}

@Composable
fun SJHCustomerServiceDialog(
	isVisible: Boolean,
	modifier: Modifier = Modifier,
	labelCancel: Int = R.string.common_cancel,
	label1: String,
	label2: String,
	onAction: (String) -> Unit,
) {
	
	BaseDialog(
		isVisible = isVisible,
		onDismiss = { onAction("") }
	) {
		Box(
			modifier = Modifier
				.padding(16.dp)
				.padding(bottom = 32.dp)
		) {
			Column(
				modifier = modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.clip(RoundedCornerShape(size = 13.dp))
						.background(Color(0xF2F5F5F5))
				) {
					Box(
						contentAlignment = Alignment.Center,
						modifier = modifier
							.fillMaxWidth()
							.height(60.dp)
							.clickable { },
					) {
						Text(
							label1,
							style = MaterialTheme.typography.displaySmall.copy(
								color = JPCSColors.blue,
								fontSize = 20.sp,
								fontWeight = FontWeight.W400,
								textAlign = TextAlign.Center,
							),
						)
					}
					Divider(
						thickness = 1.dp,
						color = JPCSColors.greyText,
						modifier = modifier
					)
					Box(
						contentAlignment = Alignment.Center,
						modifier = modifier
							.fillMaxWidth()
							.height(60.dp)
							.clickable {  },
					) {
						Text(
							label2,
							style = MaterialTheme.typography.displaySmall.copy(
								color = JPCSColors.blue,
								fontSize = 20.sp,
								fontWeight = FontWeight.W400,
								textAlign = TextAlign.Left,
							),
						)
					}
				}
				Spacer(modifier = modifier.height(10.dp))
				Box(
					contentAlignment = Alignment.Center,
					modifier = modifier
						.fillMaxWidth()
						.height(60.dp)
						.clip(shape = RoundedCornerShape(size = 13.dp))
						.background(color = colorResource(id = R.color.white))
						.clickable { onAction("") },
				) {
					Text(
						stringResource(id = labelCancel),
						style = MaterialTheme.typography.displaySmall.copy(
							color = JPCSColors.blue,
							fontSize = 20.sp,
							fontWeight = FontWeight.W400,
							textAlign = TextAlign.Left,
						),
					)
				}
				Spacer(modifier = modifier.height(16.dp))
			}
		}
	}
}