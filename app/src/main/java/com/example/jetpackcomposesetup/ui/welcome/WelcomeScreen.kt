package com.example.jetpackcomposesetup.ui.welcome

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jetpackcomposesetup.ui.component.TopShadowGradient
import com.example.jetpackcomposesetup.ui.component.WelcomeTopAppBar
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.triggerRebirth
import com.example.jetpackcomposesetup.ui.asLang
import com.example.jetpackcomposesetup.ui.component.CustomHorizontalPagerIndicator
import com.example.jetpackcomposesetup.ui.component.DefaultButton
import com.example.jetpackcomposesetup.ui.component.MainBottomGradient
import com.example.jetpackcomposesetup.ui.webview.PageContent


@Composable
internal fun WelcomeRoute(
	onBackPress: () -> Unit,
	navigateToLogin: () -> Unit,
	navigateToRegister: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: WelcomeViewModel = hiltViewModel()
) {
	val characterUiState by viewModel.characterUiState.collectAsState()
	
	WelcomeScreen(
		onLogin = navigateToLogin,
		onRegister = navigateToRegister,
		modifier = modifier,
		onClose = onBackPress,
		characterUiState = characterUiState
	)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun WelcomeScreen(
	onLogin: () -> Unit,
	onRegister: () -> Unit,
	onClose: () -> Unit,
	characterUiState : CharacterUiState,
	modifier: Modifier = Modifier,
) {
	
	Scaffold(topBar = {
		TopShadowGradient(
			modifier = Modifier.height(172.dp)
		) {
			WelcomeTopAppBar(
				onCloseClick = onClose,
			)
		}
	}) { padding ->
		Box(
			modifier = modifier
				.fillMaxSize()
				.padding(bottom = padding.calculateBottomPadding())
		) {
			Column(
				modifier = modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Bottom,
				horizontalAlignment = Alignment.CenterHorizontally
			) {

				var pageCount by remember {
					mutableIntStateOf(0)
				}
				val pagerState = rememberPagerState(
					initialPage = 0,
					initialPageOffsetFraction = 0f
				) {
					pageCount
				}

				when (characterUiState) {
					is CharacterUiState.Success -> {
						val items = characterUiState.items
						pageCount = items.size
						HorizontalPager(
							modifier = Modifier
								.fillMaxWidth()
								.weight(1f),
							state = pagerState,
							pageSpacing = 0.dp,
							userScrollEnabled = true,
							reverseLayout = false,
							contentPadding = PaddingValues(0.dp),
							beyondBoundsPageCount = 0,
							pageSize = PageSize.Fill,
							flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
							key = null,
							pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
								Orientation.Horizontal
							),
							pageContent = {index ->
								val item = items[index]
								Box(
									Modifier.fillMaxSize(),
								) {
									AsyncImage(
										model = item.image,
										contentDescription = null,
										contentScale = ContentScale.Crop,
										modifier = Modifier.fillMaxSize()
									)
									MainBottomGradient(
										modifier = Modifier
											.height(313.dp)
											.align(Alignment.BottomCenter)
									) {
										Column(
											modifier = Modifier
												.fillMaxWidth()
												.wrapContentHeight()
												.height(313.dp),
											verticalArrangement = Arrangement.Bottom,
											horizontalAlignment = Alignment.CenterHorizontally
										) {

											Text(
												text = item.name ?: "",
												style = MaterialTheme.typography.headlineSmall.copy(
													color = Color.White
												),
												fontSize = 26.sp,
												fontWeight = FontWeight.Bold,
												textAlign = TextAlign.Center,
												modifier = Modifier.padding(horizontal = 32.dp),
												maxLines = 4,
												overflow = TextOverflow.Ellipsis
											)
										}
									}
								}
							}
						)
					}

					is CharacterUiState.Loading -> {
						Box(
							Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							CircularProgressIndicator()
						}
					}

					else -> Unit
				}

				Box(
					Modifier
						.fillMaxWidth()
						.background(MaterialTheme.colorScheme.primary)
						.padding(vertical = 24.dp),
					contentAlignment = Alignment.Center
				) {
					if (pageCount > 1) {
						CustomHorizontalPagerIndicator(
							pagerState = pagerState,
							pageCount = pageCount,
							space = 8.dp,
						)
					} else {
						Spacer(modifier = Modifier.size(6.dp))
					}
				}
				
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.background(color = MaterialTheme.colorScheme.primary)
						.padding(vertical = 12.dp)
				) {
					DefaultButton(
						text = stringResource(id = R.string.login_signin_title),
						onClick = onLogin,
						buttonColor = Color.White,
						textColor = Color.Black,
						modifier = Modifier
							.padding(horizontal = 16.dp)
							.fillMaxWidth()
					)
					Spacer(modifier = Modifier.size(20.dp))
					DefaultButton(
						text = "Register",
						onClick = onRegister,
						buttonColor = Color.Black,
						textColor = Color.White,
						modifier = Modifier
							.padding(horizontal = 16.dp)
							.fillMaxWidth()
					)
					val annotatedString = buildAnnotatedString {
						append("term condition private text")
						append(" ")

						pushStringAnnotation(
							PageContent.TermService.slug,
							PageContent.TermService.slug
						)
						withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.W500)) {
							append("term of service")
						}
						pop()

						append(" ")
						append("and")
						append(" ")

						pushStringAnnotation(
							PageContent.PrivacyPolicy.slug,
							PageContent.PrivacyPolicy.slug
						)
						withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.W500)) {
							append("privacy_policy")
						}
						pop()
					}

					ClickableText(
						text = annotatedString,
						style = TextStyle.Default.copy(
							color = Color.White.copy(alpha = .7f),
							textAlign = TextAlign.Center
						),
						modifier = Modifier
							.fillMaxWidth()
							.padding(top = 40.dp, bottom = 32.dp)
							.padding(horizontal = 48.dp),
						onClick = { i ->
							annotatedString.getStringAnnotations(i, i)
								.firstOrNull()
								?.let {
									when (it.tag) {
										PageContent.TermService.slug -> {
											 // TODO :
										}
										PageContent.PrivacyPolicy.slug -> {
											// TODO :
										}
									}
								}
						}
					)
				}
			}
		}
	}
}