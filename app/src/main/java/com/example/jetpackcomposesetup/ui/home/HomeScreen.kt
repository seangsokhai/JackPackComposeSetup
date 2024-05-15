@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposesetup.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposesetup.ui.component.CustomAsyncImage
import com.example.jetpackcomposesetup.ui.component.EmptyView
import com.example.jetpackcomposesetup.ui.component.HomeTopAppBar
import com.example.jetpackcomposesetup.data.model.CharacterDto
import com.example.jetpackcomposesetup.ui.AppStateBottomBar
import com.example.jetpackcomposesetup.ui.component.FullScreenLoadingIndicator
import com.example.jetpackcomposesetup.ui.theme.JPCSColors
import com.example.jetpackcomposesetup.ui.welcome.CharacterUiState
import java.util.*

@Composable
internal fun HomeRoute(
	navigateToContactUs: () -> Unit,
	navigateToNotification: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: HomeViewModel = hiltViewModel()
) {
	val characterUiState by viewModel.characterUiState.collectAsState()

	HomeScreen(
		modifier = modifier,
		navigateToContactUs = navigateToContactUs,
		navigateToNotification = navigateToNotification,
		characterUiState = characterUiState,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
	navigateToContactUs: () -> Unit,
	navigateToNotification: () -> Unit,
	modifier: Modifier = Modifier,
	characterUiState : CharacterUiState,
) {
	val scrollState = rememberScrollState()
	Scaffold(
		topBar = {
			HomeTopAppBar(
				modifier = modifier,
				onActionClick = navigateToNotification,
				onContactUsClick = navigateToContactUs,
			)
		},
		bottomBar = {
			AppStateBottomBar()
		},
	) { padding ->

		Column(
			modifier = modifier
				.fillMaxSize()
				.background(Color(0xFFF6F8FA))
				.padding(top = padding.calculateTopPadding())
				.padding(bottom = padding.calculateBottomPadding())
				.padding(bottom = 16.dp),
		) {
			when (characterUiState) {
				is CharacterUiState.Success -> {
					val items = characterUiState.items
					LazyColumn(
						modifier = modifier.fillMaxSize(),
						contentPadding = PaddingValues(16.dp),
						verticalArrangement = Arrangement.spacedBy(8.dp),
					) {
						items(items.size) { index ->
							CharacterItem(
								item = items[index],
								onItemClick = {
									println(">>> selected character ${it.name}")
								}
							)
							Spacer(modifier = Modifier.size(10.dp))
						}
					}
				}

				is CharacterUiState.Loading -> {
					FullScreenLoadingIndicator()
				}

				else -> EmptyView()
			}
		}
	}
}

@Composable
private fun CharacterItem(
	modifier : Modifier = Modifier,
	item: CharacterDto,
	onItemClick: (CharacterDto) -> Unit
) {
	Box(
		modifier = modifier
			.clip(RoundedCornerShape(12.dp))
			.clickable { onItemClick(item) }
			.fillMaxWidth()
			.background(color = Color.White)
			.padding(16.dp),
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center,
		) {
			CustomAsyncImage(
				url = item.image,
				modifier = modifier
					.size(80.dp)
					.clip(CircleShape)
			)
			Spacer(modifier = modifier.size(8.dp))
			Column {
				Text(
					text = item.name ?: "",
					style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp, fontWeight = FontWeight.W500),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
				)
				Text(
					text = item.gender ?: "",
					style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.W400, color = JPCSColors.greyText),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
				)
				Spacer(modifier = modifier.size(8.dp))
				Row(
					modifier = modifier
						.background(
							color = JPCSColors.greyContainer,
							shape = RoundedCornerShape(8.dp)
						)
						.padding(vertical = 9.dp, horizontal = 6.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					Text(
						text = item.status ?: "",
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
					)
				}
			}
		}
	}
}


