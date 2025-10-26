package com.example.catslearningcompose.ui.screens.edit

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
import com.example.catslearningcompose.model.LoadResult
import com.example.catslearningcompose.ui.components.ExceptionToMessageMapper
import com.example.catslearningcompose.ui.components.ItemDetails
import com.example.catslearningcompose.ui.components.ItemDetailsState
import com.example.catslearningcompose.ui.components.LoadResultContent
import com.example.catslearningcompose.ui.screens.EventConsumer
import com.example.catslearningcompose.ui.screens.ItemsGraph.EditItemRoute
import com.example.catslearningcompose.ui.screens.LocalNavController
import com.example.catslearningcompose.ui.screens.edit.EditItemViewModel.ScreenState
import com.example.catslearningcompose.ui.screens.routeClass

@Composable
fun EditItemScreen(
    index: Int,
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current

    EventConsumer(channel = viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.popBackStack()
        }
    }
    EventConsumer(viewModel.errorChannel) { exception ->
        val message = exceptionToMessageMapper.getUserMessage(exception, context)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    EditItemContent(
        loadResult = screenState,
        onEditButtonClicked = viewModel::update,
        onTryAgainButtonClick = viewModel::loadItem
    )
}

@Composable
fun EditItemContent(
    loadResult: LoadResult<ScreenState>,
    onEditButtonClicked: (String) -> Unit,
    onTryAgainButtonClick: () -> Unit
) {
    LoadResultContent(
        loadResult = loadResult,
        content = { screenState ->
            SuccessEditItemContent(
                state = screenState,
                onEditButtonClicked = onEditButtonClicked
            )
        },
        onTryAgainAction = onTryAgainButtonClick
    )
}

@Composable
private fun SuccessEditItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = state.loadedItem,
            textFieldPlaceholder = stringResource(R.string.outlined_text_field_placeholder_edit_text),
            actionButtonText = stringResource(R.string.edit_button_title),
            isActionInProgress = state.isEditInProgress
        ),
        onActionButtonClick = onEditButtonClicked,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
fun EditItemScreenPreview() {
    EditItemContent(
        loadResult = LoadResult.Success(
            ScreenState(
                loadedItem = "Item 1",
                isEditInProgress = false
            )
        ),
        onEditButtonClicked = {},
        onTryAgainButtonClick = {}
    )
}
