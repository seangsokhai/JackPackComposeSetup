package com.example.jetpackcomposesetup.ui.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.component.BackTopAppBar
import com.example.jetpackcomposesetup.ui.component.ClickText
import com.example.jetpackcomposesetup.ui.component.DefaultButton
import com.example.jetpackcomposesetup.ui.component.DefaultOutlineTextField
import com.example.jetpackcomposesetup.ui.component.ErrorDialog
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme

@Composable
internal fun LoginCodeRoute(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val showError by remember {
        mutableStateOf(false)
    }
    val errorMessage: String by remember {
        mutableStateOf("Invalid verification code")
    }

    val activity = LocalContext.current as Activity

    LoginCodeScreen(
        onBackClick = onBackClick,
        onResendClick = {

        },
        onSmsCodeEntered = { smsCode ->
            onSuccess()
        },
        modifier = modifier,
        phoneNumber = "",
        timer = "2"
    )

    ErrorDialog(
        isVisible = showError,
        title = stringResource(id = R.string.common_error),
        message = errorMessage
    ) {

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun LoginCodeScreen(
    onBackClick: () -> Unit,
    onResendClick: () -> Unit,
    onSmsCodeEntered: (String) -> Unit,
    phoneNumber: String,
    timer: String,
    modifier: Modifier = Modifier,
    showChangeNumberButton: Boolean = false
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        BackTopAppBar(
            onNavigationClick = onBackClick
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .imePadding()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Text(
                text = stringResource(R.string.otp_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "${stringResource(id = R.string.otp_subtitle)} $phoneNumber",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF777F89)
            )
            Spacer(modifier = Modifier.size(30.dp))

            var code: String by rememberSaveable { mutableStateOf("") }

            DefaultOutlineTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 6) code = it
                    if (code.length >= 6) {
                        focusManager.clearFocus()
                        onSmsCodeEntered(code)
                    }
                },
                placeholder = {
                    Text(
                        text = "\u2022".repeat(6),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 41.sp,
                            color = Color(0xFFDDDDDD)
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 41.sp,
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            keyboardController?.show()
                        }
                    },
            )

            Spacer(modifier = Modifier.weight(1f))

            if (showChangeNumberButton) {
                ClickText(
                    text = stringResource(id = R.string.change_phone_number),
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    onBackClick()
                }
                Spacer(modifier = Modifier.size(16.dp))
            }

            val counter = if (timer == "2:00") "" else timer
            val label =
                if (counter.isNotEmpty()) "${stringResource(id = R.string.otp_resent_code)} $counter"
                else stringResource(id = R.string.otp_resend_code_title)
            val enabled = counter.isEmpty()
            DefaultButton(
                text = label,
                onClick = {
                    code = ""
                    onResendClick()
                },
                modifier = modifier.fillMaxWidth(),
                enabled = enabled
            )
        }
    }
}

@Preview
@Composable
fun LoginCodePreview() {
    JetpackComposeSetupTheme {
        LoginCodeScreen(
            onBackClick = {},
            onResendClick = {},
            onSmsCodeEntered = {},
            phoneNumber = "+85578300124",
            timer = "2:22"
        )
    }
}
