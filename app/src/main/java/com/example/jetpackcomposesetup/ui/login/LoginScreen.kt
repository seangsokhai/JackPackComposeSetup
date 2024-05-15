package com.example.jetpackcomposesetup.ui.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.component.BackTopAppBar
import com.example.jetpackcomposesetup.ui.component.ClickText
import com.example.jetpackcomposesetup.ui.component.CustomTextField
import com.example.jetpackcomposesetup.ui.component.DefaultButton
import com.example.jetpackcomposesetup.ui.component.ErrorDialog
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
internal fun LoginRoute(
	onBackClick: () -> Unit,
	navigateToLoginCode: () -> Unit,
	navigateToContactUs: () -> Unit,
	modifier: Modifier = Modifier,
	isOffline: StateFlow<Boolean>,
	viewModel: LoginViewModel = hiltViewModel()
) {
	val focusManager = LocalFocusManager.current
	val activity = LocalContext.current as Activity
	
	val showError by remember { mutableStateOf(false) }
	val errorMessage: String by remember { mutableStateOf("") }
	var showConnection by remember { mutableStateOf(false) }
	var showContactDialog: Boolean by remember { mutableStateOf(false) }
	

	LoginScreen(
		onBackClick = onBackClick,
		onContinueClick = navigateToLoginCode,
		modifier = modifier,
		focusManager = focusManager,
		onContactUsClick = {}
	)

	
	//TODO
	ErrorDialog(
		isVisible = showError,
		title = "Error",
		message = errorMessage,
		onAction = {}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
	onBackClick: () -> Unit,
	onContinueClick: () -> Unit,
	modifier: Modifier = Modifier,
	focusManager: FocusManager,
	onContactUsClick: () -> Unit,
) {
	Scaffold(
		topBar = {
			BackTopAppBar(
				onNavigationClick = onBackClick
			)
		},
		modifier = modifier
			.pointerInput(Unit) {
				detectTapGestures(onTap = {
					focusManager.clearFocus()
				})
			}
	) { padding ->
		Column(
			modifier = modifier
				.fillMaxSize()
				.imePadding()
				.padding(top = padding.calculateTopPadding())
				.systemBarsPadding()
				.padding(horizontal = 16.dp)
				.padding(bottom = 16.dp)
				.verticalScroll(rememberScrollState())
		
		) {
			Text(
				text = stringResource(id = R.string.login_signin_title),
				style = MaterialTheme.typography.displaySmall,
				fontWeight = FontWeight.Bold
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				stringResource(R.string.login_signin_subtitle),
				style = MaterialTheme.typography.bodyMedium,
				color = Color(0xFF777F89)
			)
			Spacer(modifier = Modifier.size(30.dp))

			var phoneNumber: String by rememberSaveable { mutableStateOf("") }

			var isFocusPhoneField by remember { mutableStateOf(false) }
			val colorBorderPhoneField = if (isFocusPhoneField) Color(0xFF767676) else Color.Unspecified

			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				Box(
					modifier = Modifier
						.size(width = 62.dp, height = 56.dp)
						.clip(ShapeDefaults.Small)
						.background(Color(0xFFF6F8FA)),
					contentAlignment = Alignment.Center
				) {
					Text("+855")
				}
				Spacer(modifier = Modifier.size(10.dp))
				CustomTextField(
					value = phoneNumber,
					onValueChange = { phoneNumber = it },
					label = {
						Text(
							stringResource(R.string.common_phone_number),
							style = TextStyle(
								color = Color(0xFF767676)
							)
						)
					},
					singleLine = true,
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Done,
						keyboardType = KeyboardType.Phone
					),
					keyboardActions = KeyboardActions(
						onDone = {  onContinueClick() }
					),
					modifier = modifier
						.weight(1f)
						.height(56.dp)
						.border(2.dp, colorBorderPhoneField, ShapeDefaults.Medium)
						.onFocusChanged {
							isFocusPhoneField = it.isFocused
						}
				)
			}
			
			ClickText(
				stringResource(id = R.string.login_phone_issue),
				modifier = Modifier.padding(vertical = 20.dp),
				textStyle = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.Bold
				)
			) {
				onContactUsClick()
			}
			
			Spacer(modifier = Modifier.weight(1f))

			val shouldContinue = phoneNumber.length >= 8

			DefaultButton(
				text = stringResource(id = R.string.common_continue),
				onClick = {
					onContinueClick()
				},
				modifier = modifier.fillMaxWidth(),
				enabled = shouldContinue
			)
		}
	}
}

@Preview
@Composable
fun LoginScreenPreview() {
	JetpackComposeSetupTheme {
		LoginScreen(
			onBackClick = {},
			onContinueClick = {},
			focusManager = LocalFocusManager.current,
			onContactUsClick = {}
		)
	}
}

