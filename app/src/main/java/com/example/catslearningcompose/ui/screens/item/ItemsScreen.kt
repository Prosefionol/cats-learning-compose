package com.example.catslearningcompose.ui.screens.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catslearningcompose.ui.screens.AddItemRoute
import com.example.catslearningcompose.ui.screens.LocalNavController

@Composable
fun ItemsScreen() {
    val navController = LocalNavController.current
    ItemsContent(
        onLaunchAddItemScreen = { navController.navigate(AddItemRoute) }
    )
}

@Composable
fun ItemsContent(onLaunchAddItemScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Items Screen",
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 22.sp
        )
        FloatingActionButton(
            onClick = onLaunchAddItemScreen,
            modifier = Modifier
                .padding(bottom = 25.dp, end = 15.dp)
                .align(Alignment.BottomEnd),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsScreenPreview() {
    ItemsContent(
        onLaunchAddItemScreen = {}
    )
}
