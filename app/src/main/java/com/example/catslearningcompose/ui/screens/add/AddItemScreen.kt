package com.example.catslearningcompose.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catslearningcompose.R
import com.example.catslearningcompose.ui.screens.AddItemRoute
import com.example.catslearningcompose.ui.screens.EventConsumer
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.add.AddItemViewModel.ScreenState

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()
    AddItemContent(
        screenState = screenState,
        onAddItemClicked = viewModel::add
    )
    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry?.destination?.route == AddItemRoute) {
            navController.popBackStack()
        }
    }
}

@Composable
fun AddItemContent(
    screenState: ScreenState,
    onAddItemClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var inputText by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = {
                Text(stringResource(R.string.outlined_text_field_placeholder_text))
            },
            enabled = screenState.isTextInputEnabled
        )
        Button(
            onClick = { onAddItemClicked(inputText) },
            enabled = screenState.isAddButtonEnabled(inputText)
        ) {
            Text(
                text = stringResource(R.string.add_button_title),
                fontSize = 12.sp
            )
        }
        Box(
            modifier = Modifier
                .size(32.dp)
        ) {
            if (screenState.isProgressVisible) {
                CircularProgressIndicator(Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddItemScreenPreview() {
    AddItemContent(
        screenState = ScreenState(
            isAddInProgress = false
        ),
        onAddItemClicked = {}
    )
}
