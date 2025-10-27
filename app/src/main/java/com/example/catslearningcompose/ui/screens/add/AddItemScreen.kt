package com.example.catslearningcompose.ui.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catslearningcompose.R
import com.example.catslearningcompose.ui.components.ExceptionToMessageMapper
import com.example.catslearningcompose.ui.components.ItemDetails
import com.example.catslearningcompose.ui.components.ItemDetailsState
import com.example.catslearningcompose.ui.screens.EventConsumer
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.add.AddItemViewModel.ScreenState

@Composable
fun AddItemScreen(
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    val viewModel: AddItemViewModel = hiltViewModel()
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current
    AddItemContent(
        screenState = screenState,
        onAddButtonClicked = viewModel::add
    )
    EventConsumer(viewModel.exitChannel) {
        navController.popBackStack()
    }
    EventConsumer(viewModel.errorChannel) { exception ->
        val message = exceptionToMessageMapper.getUserMessage(exception, context)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
