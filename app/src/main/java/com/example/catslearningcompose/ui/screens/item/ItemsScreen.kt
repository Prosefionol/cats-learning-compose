package com.example.catslearningcompose.ui.screens.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.catslearningcompose.ui.screens.item.ItemsViewModel.*

@Composable
fun ItemsScreen() {
    val viewModel: ItemsViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()
    ItemsContent(
        getScreenState = { screenState.value }
    )
}

@Composable
fun ItemsContent(
    getScreenState: () -> ScreenState
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
                    items(screenState.items) {
                        Text(
                            text = it,
                            fontSize = 15.sp,
                            modifier = Modifier
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
        getScreenState = { ScreenState.Loading }
    )
}
