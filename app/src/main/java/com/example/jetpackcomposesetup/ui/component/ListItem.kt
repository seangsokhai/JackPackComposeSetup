package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseListItem(
    modifier: Modifier = Modifier,
    leadingContent: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
    verticalAlignment : Alignment.Vertical = Alignment.CenterVertically
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment
    ) {
        leadingContent()
        Column(modifier = Modifier.weight(1f)) {
            content()
        }
        trailingContent()
    }
}