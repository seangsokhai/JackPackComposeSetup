package com.example.jetpackcomposesetup.ui.more


import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.ui.more.component.ChooseLanguageDialog
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.triggerRebirth
import com.example.jetpackcomposesetup.ui.AppStateBottomBar
import com.example.jetpackcomposesetup.ui.asLang
import com.example.jetpackcomposesetup.ui.component.LoadingDialog
import com.example.jetpackcomposesetup.ui.component.MoreGradientBackground
import com.example.jetpackcomposesetup.ui.component.ResIcon
import com.example.jetpackcomposesetup.ui.theme.JPCSColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.TextStyle
import java.util.*


@Composable
internal fun MoreRoute(
	navigateToLogin: () -> Unit,
	navigateToProfile: () -> Unit,
	navigateToGreeting: () -> Unit,
	navigateToContactUs: () -> Unit,
	navigateToFeedback: () -> Unit,
	navigateToPrivacyPolicy: () -> Unit,
	navigateToTermsOfService: () -> Unit,
	onLanguageChange: () -> Unit,
	onLogOut: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: MoreViewModel = hiltViewModel()
) {

	val context = LocalContext.current
	val activity = LocalContext.current as Activity
	val scope = rememberCoroutineScope()

	var shouldChooseLanguage by remember { mutableStateOf(false) }
	var shouldShowLogOut by remember { mutableStateOf(false) }
	var showLoadingDialog by remember { mutableStateOf(false) }

	LaunchedEffect(showLoadingDialog) {
		if (showLoadingDialog) {
			scope.launch {
				delay(1200L)
				showLoadingDialog = false
			}
		}
	}

	MoreScreen(
		navigateToLogin = navigateToLogin,
		navigateToProfile = navigateToProfile,
		onLanguageClick = { shouldChooseLanguage = true },
		onGreetingIntroClick = navigateToGreeting,
		onContactUsClick = navigateToContactUs,
		onFeedbackClick = navigateToFeedback,
		onPrivacyPolicyClick = navigateToPrivacyPolicy,
		onTermsOfServiceClick = navigateToTermsOfService,
		onLogOutClick = {
			shouldShowLogOut = true
		},
	)

	ChooseLanguageDialog(
		isVisible = shouldChooseLanguage,
		onClose = {
			shouldChooseLanguage = false
		},
		onLanguageChange = { lang ->
			viewModel.saveUserLanguage(lang)
			viewModel.clearCache()
			AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
			shouldChooseLanguage = false
			triggerRebirth(context, activity)
		},
		currentLang = viewModel.getUserLanguage().asLang(),
	)

	LoadingDialog(isLoading = showLoadingDialog)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoreScreen(
	navigateToLogin: () -> Unit,
	navigateToProfile: () -> Unit,
	onLanguageClick: () -> Unit,
	onGreetingIntroClick: () -> Unit,
	onContactUsClick: () -> Unit,
	onFeedbackClick: () -> Unit,
	onPrivacyPolicyClick: () -> Unit,
	onTermsOfServiceClick: () -> Unit,
	onLogOutClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Scaffold(
		bottomBar = {
			AppStateBottomBar()
		}
	) { padding ->

		val isLoggedIn by remember {
			mutableStateOf(false)
		}

		MoreGradientBackground(
			topColor = MaterialTheme.colorScheme.tertiary,
			bottomColor = Color(0xFFF6F8FA)
		) {
			Column(
				modifier = modifier
					.fillMaxSize()
					.verticalScroll(rememberScrollState())
					.padding(top = padding.calculateTopPadding())
					.padding(bottom = padding.calculateBottomPadding())
			) {
				Text(
					text = stringResource(id = R.string.common_more),
					fontSize = 22.sp,
					fontWeight = FontWeight.W500,
					modifier = Modifier.padding(16.dp),
				)

				ListItem(leadingContent = {
					ResIcon(icon = R.drawable.ic_profile_default)
				}, headlineText = {
					Text(
						text = stringResource(id = R.string.sign_in_register),
						fontSize = 15.sp,
						fontWeight = FontWeight.Bold,
					)
					Spacer(modifier = modifier.size(2.dp))
				}, supportingText = {
					Text(
						text = stringResource(id = R.string.profile_not_logged_in_subtitle), fontSize = 13.sp
					)
				}, trailingContent = {
					ResIcon(
						icon = R.drawable.ic_arrow_right,
						modifier = Modifier
							.size(12.dp)
					)
				}, colors = ListItemDefaults.colors(
					containerColor = Color.Unspecified, supportingColor = Color(0xFF767676)
				), modifier = Modifier.clickable(onClick = navigateToLogin)
				)

				Text(
					text = stringResource(id = R.string.common_general),
					fontSize = 13.sp,
					fontWeight = FontWeight.W500,
					color = Color(0xFF777F89),
					modifier = Modifier
						.padding(horizontal = 16.dp)
						.padding(bottom = 10.dp, top = 20.dp)
				)

				Column(
					Modifier
						.padding(horizontal = 16.dp)
						.fillMaxWidth()
						.clip(ShapeDefaults.Medium)
						.background(Color.White)
				) {
					// Language
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_language)
					}, headlineText = {
						Text(stringResource(id = R.string.profile_language), fontSize = 15.sp)
					}, trailingContent = {
						Text(
							stringResource(id = R.string.current_languge),
							fontSize = 15.sp,
							color = Color(0xFF767676)
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onLanguageClick)
					)

					// Greeting & Intro
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_greetting_intro)
					}, headlineText = {
						Text(
							stringResource(id = R.string.profile_greeting_introduction), fontSize = 15.sp
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onGreetingIntroClick)
					)

					// Contact
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_contact_us)
					}, headlineText = {
						Text(
							stringResource(id = R.string.profile_contact_us), fontSize = 15.sp
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onContactUsClick)
					)

					// Feedback
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_feedback)
					}, headlineText = {
						Text(
							stringResource(id = R.string.profile_feedback), fontSize = 15.sp
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onFeedbackClick)
					)

					// Privacy
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_privacy_policy)
					}, headlineText = {
						Text(
							stringResource(id = R.string.profile_privacy_policy), fontSize = 15.sp
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onPrivacyPolicyClick)
					)

					// Terms
					ListItem(leadingContent = {
						ResIcon(icon = R.drawable.ic_terms_of_service)
					}, headlineText = {
						Text(
							stringResource(id = R.string.profile_term_of_service), fontSize = 15.sp
						)
					}, colors = ListItemDefaults.colors(
						containerColor = Color.Unspecified,
					), modifier = Modifier
						.fillMaxWidth()
						.clickable(onClick = onTermsOfServiceClick)
					)
				}

				if (isLoggedIn) {
					// Logout
					ListItem(
						leadingContent = {
							ResIcon(icon = R.drawable.ic_logout)
						},
						headlineText = {
							Text(
								stringResource(id = R.string.logout),
								style = MaterialTheme.typography.bodyMedium.copy(
									fontSize = 15.sp, color = JPCSColors.red, fontWeight = FontWeight.W500
								),
							)
						},
						colors = ListItemDefaults.colors(
							containerColor = Color.White,
						),
						modifier = Modifier
							.padding(horizontal = 16.dp)
							.padding(vertical = 20.dp)
							.fillMaxWidth()
							.clip(ShapeDefaults.Medium)
							.clickable(onClick = onLogOutClick)
					)
				}
			}
		}
	}
}