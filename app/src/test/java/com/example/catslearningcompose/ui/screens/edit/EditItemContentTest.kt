package com.example.catslearningcompose.ui.screens.edit

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.catslearningcompose.ComposeTest
import com.example.catslearningcompose.TestTags
import com.example.catslearningcompose.model.LoadResult
import com.example.catslearningcompose.ui.screens.edit.EditItemViewModel.ScreenState
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test

class EditItemContentTest: ComposeTest() {
    @RelaxedMockK
    private lateinit var onEditButtonClicked: (String) -> Unit

    @RelaxedMockK
    private lateinit var onTryAgainButtonClick: () -> Unit

    @Test
    fun editItemContent_withLoadedItem_showsItem() = runComposeTest {
        setupSuccessEditItemContent(loadedItem = "Item", isEditInProgress = false)

        onNodeWithTag(TestTags.OutlinedTextField).assertTextEquals("Item")
    }

    @Test
    fun clickOnSubmitButton_executesEditAction() = runComposeTest {
        setupSuccessEditItemContent(loadedItem = "Item", isEditInProgress = false)

        onNodeWithTag(TestTags.SubmitButton).performClick()
        verify(exactly = 1) {
            onEditButtonClicked(any<String>())
        }
    }

    private fun ComposeContentTestRule.setupSuccessEditItemContent(
        loadedItem: String,
        isEditInProgress: Boolean
    ) {
        setContent {
            EditItemContent(
                loadResult = LoadResult.Success(
                    ScreenState(
                        loadedItem = loadedItem,
                        isEditInProgress = isEditInProgress
                    )
                ),
                onEditButtonClicked = onEditButtonClicked,
                onTryAgainButtonClick = onTryAgainButtonClick
            )
        }
    }
}
