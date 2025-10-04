package com.example.catslearningcompose.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.catslearningcompose.R
import kotlinx.collections.immutable.persistentListOf

data class AppTab(
    val icon: ImageVector,
    @StringRes val labelRes: Int,
    val graph: Any
)

val MainTabs = persistentListOf(
    AppTab(
        icon = Icons.Default.AccountBox,
        labelRes = R.string.profile_screen_title,
        graph = ProfileGraph
    ),
    AppTab(
        icon = Icons.AutoMirrored.Default.List,
        labelRes = R.string.item_screen_title,
        graph = ItemsGraph
    ),
    AppTab(
        icon = Icons.Default.Settings,
        labelRes = R.string.settings_screen_title,
        graph = SettingsGraph
    )
)
