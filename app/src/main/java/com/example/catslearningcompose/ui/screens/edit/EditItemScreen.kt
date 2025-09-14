package com.example.catslearningcompose.ui.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.catslearningcompose.ui.screens.EditItemRoute
import com.example.catslearningcompose.ui.screens.EventConsumer
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.edit.EditItemViewModel.ScreenState
import com.example.catslearningcompose.ui.screens.routeClass

@Composable
fun EditItemScreen(
    index: Int
) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()

    EventConsumer(channel = viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.popBackStack()
        }
    }

    EditItemContent(
        state = screenState,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            ScreenState.Loading -> CircularProgressIndicator()
            is ScreenState.Success -> SuccessEditItemContent(state, onEditButtonClicked)
        }
    }

}

@Composable
private fun SuccessEditItemContent(
    state: ScreenState.Success,
    onEditButtonClicked: (String) -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf(state.loadedItem) }
    OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = {
            Text(stringResource(R.string.outlined_text_field_placeholder_edit_text))
        },
        enabled = !state.isEditInProgress
    )
    Spacer(modifier = Modifier
        .height(12.dp)
    )
    Button(
        onClick = { onEditButtonClicked(inputText) },
        enabled = inputText.isNotBlank() && !state.isEditInProgress
    ) {
        Text(
            text = stringResource(R.string.edit_button_title),
            fontSize = 12.sp
        )
    }
    Spacer(modifier = Modifier
        .height(12.dp)
    )
    Box(
        modifier = Modifier
            .size(32.dp)
    ) {
        if (state.isEditInProgress) {
            CircularProgressIndicator(Modifier.fillMaxSize())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EditItemScreenPreview() {
    EditItemContent(
        state = ScreenState.Success(
            loadedItem = "Item 1",
            isEditInProgress = false
        ),
        onEditButtonClicked = {}
    )
}
