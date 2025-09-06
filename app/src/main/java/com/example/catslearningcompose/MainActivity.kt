package com.example.catslearningcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catslearningcompose.model.ItemsRepository
import com.example.catslearningcompose.ui.screens.AddItemRoute
import com.example.catslearningcompose.ui.screens.ItemsRoute
import com.example.catslearningcompose.ui.screens.LocalNavController
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
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = ItemsRoute,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(ItemsRoute) { ItemsScreen() }
            composable(AddItemRoute) { AddItemScreen() }
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
