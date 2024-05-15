package com.example.jetpackcomposesetup.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.BottomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = BottomNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = BottomNavigationDefaults.navigationContentColor(),
            selectedTextColor = BottomNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = BottomNavigationDefaults.navigationContentColor(),
            indicatorColor = BottomNavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(shadowElevation = 8.dp) {
        NavigationBar(
            modifier = modifier,
            contentColor = BottomNavigationDefaults.navigationContentColor(),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            content = content
        )
    }
}

object BottomNavigationDefaults {
    @Composable
    fun navigationContentColor() = Color.Unspecified

    @Composable
    fun navigationSelectedItemColor() = Color.Unspecified

    @Composable
    fun navigationIndicatorColor() = Color.White

}
