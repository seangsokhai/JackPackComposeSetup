package com.example.jetpackcomposesetup.ui.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.component.BackTopAppBar
import com.example.jetpackcomposesetup.ui.theme.JPCSColors
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme


@Composable
internal fun WebViewRoutes(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WebViewViewModel = hiltViewModel()
) {

    WebViewScreen(
        onBackClick = onBackClick,
        modifier = modifier,
        screen = viewModel.screen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WebViewScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    screen: String
) {
    val title = 0

    Scaffold(
        topBar = {
            BackTopAppBar(
                onNavigationClick = onBackClick,
                titleRes = title,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(JPCSColors.greyContainer)
                .systemBarsPadding()
        ) {

            // TODO WEBView
            /*WebView(
                state = rememberWebViewStateWithHTMLData(
                    data = pageUiState.page.content ?: ""
                ),
                captureBackPresses = false,
                onCreated = { webView ->
                    webView.setBackgroundColor(Color.Transparent.toArgb())
                },
                modifier = Modifier.fillMaxSize().padding(10.dp)
            )*/
        }

    }

}

@Preview
@Composable
fun WebViewScreenPreview() {
    JetpackComposeSetupTheme {
        WebViewScreen(
            onBackClick = {},
            screen = ""
        )
    }
}