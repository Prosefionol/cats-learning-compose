package com.example.catslearningcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.catslearningcompose.ui.screens.AppNavigationBar
import com.example.catslearningcompose.ui.screens.AppToolbar
import com.example.catslearningcompose.ui.screens.ItemsGraph
import com.example.catslearningcompose.ui.screens.ItemsGraph.AddItemRoute
import com.example.catslearningcompose.ui.screens.ItemsGraph.EditItemRoute
import com.example.catslearningcompose.ui.screens.ItemsGraph.ItemsRoute
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.MainTabs
import com.example.catslearningcompose.ui.screens.NavigateUpAction
import com.example.catslearningcompose.ui.screens.ProfileGraph
import com.example.catslearningcompose.ui.screens.ProfileGraph.ProfileRoute
import com.example.catslearningcompose.ui.screens.SettingsGraph
import com.example.catslearningcompose.ui.screens.SettingsGraph.SettingsRoute
import com.example.catslearningcompose.ui.screens.add.AddItemScreen
import com.example.catslearningcompose.ui.screens.edit.EditItemScreen
import com.example.catslearningcompose.ui.screens.item.ItemsScreen
import com.example.catslearningcompose.ui.screens.profile.ProfileScreen
import com.example.catslearningcompose.ui.screens.routeClass
import com.example.catslearningcompose.ui.screens.settings.SettingsScreen
import com.example.catslearningcompose.ui.theme.CatsLearningComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatsLearningComposeTheme {
                CatsLearningComposeApp()
            }
        }
    }
}

@Composable
fun CatsLearningComposeApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val titleRes = when (currentBackStackEntry.routeClass()) {
        ItemsRoute::class -> R.string.item_screen_title
        AddItemRoute::class -> R.string.add_item_screen_title
        EditItemRoute::class -> R.string.edit_item_screen_title
        SettingsRoute::class -> R.string.settings_screen_title
        ProfileRoute::class -> R.string.profile_screen_title
        else -> R.string.app_name
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleRes,
                navigateUpAction = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.Hidden
                } else {
                    NavigateUpAction.Visible(
                        onClick = { navController.navigateUp() }
                    )
                }
            )
        },
        floatingActionButton = {
            if (currentBackStackEntry.routeClass() == ItemsRoute::class) {
                FloatingActionButton(
                    onClick = { navController.navigate(AddItemRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            AppNavigationBar(
                navController = navController,
                tabs = MainTabs
            )
        }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = ProfileGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                navigation<ItemsGraph>(startDestination = ItemsRoute) {
                    composable<ItemsRoute> { ItemsScreen() }
                    composable<AddItemRoute> { AddItemScreen() }
                    composable<EditItemRoute> { entry ->
                        val route: EditItemRoute = entry.toRoute()
                        EditItemScreen(route.index)
                    }
                }
                navigation<SettingsGraph>(startDestination = SettingsRoute) {
                    composable<SettingsRoute> { SettingsScreen() }
                }
                navigation<ProfileGraph>(startDestination = ProfileRoute) {
                    composable<ProfileRoute> { ProfileScreen() }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatsLearningComposePreview() {
    CatsLearningComposeTheme {
        CatsLearningComposeApp()
    }
}
