package com.example.catslearningcompose.ui.screens.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catslearningcompose.ui.screens.EditItemRoute
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.item.ItemsViewModel.*

@Composable
fun ItemsScreen() {
    val viewModel: ItemsViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()
    val navController = LocalNavController.current
    ItemsContent(
        getScreenState = { screenState.value },
        onItemClicked = { index ->
            navController.navigate(EditItemRoute(index))
        }
    )
}

@Composable
fun ItemsContent(
    getScreenState: () -> ScreenState,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val screenState = getScreenState()) {
            ScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            is ScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    itemsIndexed(screenState.items) { index, item ->
                        Text(
                            text = item,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .clickable{ onItemClicked(index) }
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsScreenPreview() {
    ItemsContent(
        getScreenState = { ScreenState.Loading },
        onItemClicked = {}
    )
}
