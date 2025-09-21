package com.example.catslearningcompose.ui.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catslearningcompose.R
import com.example.catslearningcompose.ui.components.ItemDetails
import com.example.catslearningcompose.ui.components.ItemDetailsState
import com.example.catslearningcompose.ui.screens.AddItemRoute
import com.example.catslearningcompose.ui.screens.EventConsumer
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.add.AddItemViewModel.ScreenState
import com.example.catslearningcompose.ui.screens.routeClass

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()
    AddItemContent(
        screenState = screenState,
        onAddButtonClicked = viewModel::add
    )
    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == AddItemRoute::class) {
            navController.popBackStack()
        }
    }
}

@Composable
fun AddItemContent(
    screenState: ScreenState,
    onAddButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = "",
            textFieldPlaceholder = stringResource(R.string.outlined_text_field_placeholder_text),
            actionButtonText = stringResource(R.string.add_button_title),
            isActionInProgress = screenState.isAddInProgress
        ),
        onActionButtonClick = onAddButtonClicked,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddItemScreenPreview() {
    AddItemContent(
        screenState = ScreenState(
            isAddInProgress = false
        ),
        onAddButtonClicked = {}
    )
}
