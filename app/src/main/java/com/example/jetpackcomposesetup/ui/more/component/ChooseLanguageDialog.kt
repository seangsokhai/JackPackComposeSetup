package com.example.jetpackcomposesetup.ui.more.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.ui.AppLanguage
import com.example.jetpackcomposesetup.ui.component.ContentDialog
import com.example.jetpackcomposesetup.ui.component.ResIcon
import com.example.jetpackcomposesetup.ui.theme.JPCSColors
import com.example.jetpackcomposesetup.ui.theme.JetpackComposeSetupTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLanguageDialog(
    isVisible: Boolean,
    onClose: () -> Unit,
    onLanguageChange: (String) -> Unit,
    currentLang: AppLanguage,
    modifier: Modifier = Modifier
) {

    ContentDialog(
        isVisible = isVisible,
        onDismiss = onClose,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val selectedLang by remember {
                mutableStateOf(currentLang.language)
            }
            Spacer(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 27.dp)
                    .size(36.dp, 5.dp)
                    .clip(ShapeDefaults.Small)
                    .background(Color(0x4C3C3C43))
            )
            Text(
                text = "Welcome language title",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = JPCSColors.greyText,
                    textAlign = TextAlign.Start
                ),
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(11.dp))
            AppLanguage.values().forEachIndexed { index, lang ->
                val isSelected by remember {
                    derivedStateOf {
                        selectedLang == lang.language
                    }
                }
                ListItem(
                    headlineText = {
                        Text(stringResource(lang.label))
                    },
                    leadingContent = {
                        ResIcon(icon = lang.icon)
                    },
                    modifier = modifier
                        .height(56.dp)
                        .clip(ShapeDefaults.Medium)
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            shape = ShapeDefaults.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified
                        )
                        .clickable {
                            onLanguageChange(lang.language)
                        },
                    colors = ListItemDefaults.colors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.tertiary
                        else Color(0xB3F5F5F5),
                    )
                )
                if (index < AppLanguage.values().size - 1) {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}

@Preview
@Composable
fun LanguageDialogExample() {
    JetpackComposeSetupTheme {
        ChooseLanguageDialog(
            isVisible = true,
            onClose = { },
            onLanguageChange = {},
            currentLang = AppLanguage.ENGLISH
        )
    }
}