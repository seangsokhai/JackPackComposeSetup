package com.example.jetpackcomposesetup.navigation


import com.example.jetpackcomposesetup.R

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = R.drawable.ic_nav_home_selected,
        unselectedIcon = R.drawable.ic_nav_home,
        titleTextId = R.string.navigation_tab_home
    ),
    PAGE2(
        selectedIcon = R.drawable.ic_phone,
        unselectedIcon = R.drawable.ic_phone_unselect,
        titleTextId = R.string.navigation_tab_page2
    ),
    PAGE3(
        selectedIcon = R.drawable.ic_nav_notification,
        unselectedIcon = R.drawable.ic_notification_unselected,
        titleTextId = R.string.navigation_tab_page3
    ),
    MORE(
        selectedIcon = R.drawable.ic_nav_more_selected,
        unselectedIcon = R.drawable.ic_nav_more,
        titleTextId = R.string.navigation_tab_more
    ),
}
