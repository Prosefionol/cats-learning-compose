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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.catslearningcompose.model.ItemsRepository
import com.example.catslearningcompose.ui.screens.AddItemRoute
import com.example.catslearningcompose.ui.screens.AppToolbar
import com.example.catslearningcompose.ui.screens.ItemsRoute
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.NavigateUpAction
import com.example.catslearningcompose.ui.screens.add.AddItemScreen
import com.example.catslearningcompose.ui.screens.item.ItemsScreen
import com.example.catslearningcompose.ui.theme.CatsLearningComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var itemsRepository: ItemsRepository

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
    val titleRes = when (currentBackStackEntry?.destination?.route) {
        ItemsRoute -> R.string.item_screen_title
        AddItemRoute -> R.string.add_item_screen_title
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
            if (currentBackStackEntry?.destination?.route == ItemsRoute) {
                FloatingActionButton(
                    onClick = { navController.navigate(AddItemRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = ItemsRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable(ItemsRoute) { ItemsScreen() }
                composable(AddItemRoute) { AddItemScreen() }
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
