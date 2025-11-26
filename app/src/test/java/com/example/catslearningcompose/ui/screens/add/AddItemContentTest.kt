package com.example.catslearningcompose.ui.screens.add

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.catslearningcompose.ComposeTest
import com.example.catslearningcompose.TestTags
import io.mockk.called
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test

class AddItemContentTest: ComposeTest() {
    @RelaxedMockK
    private lateinit var onAddButtonClicked: (String) -> Unit

    @Test
    fun addItemContent_withoutProgressStatus_doesNotShowProgressAndEnablesButton() = runComposeTest {
        setupAddItemContent(isAddInProgress = false)

        onNodeWithTag(TestTags.SubmitButton).assertIsEnabled()
        onNodeWithTag(TestTags.CircularProgressIndicator).assertDoesNotExist()
    }

    @Test
    fun addItemContent_withProgressStatus_showsProgressAndDisablesButton() = runComposeTest {
        setupAddItemContent(isAddInProgress = true)

        onNodeWithTag(TestTags.SubmitButton).assertIsNotEnabled()
        onNodeWithTag(TestTags.CircularProgressIndicator).assertExists()
    }

    @Test
    fun clickOnSubmitButton_executesAddAction() = runComposeTest {
        setupAddItemContent(isAddInProgress = false)

        onNodeWithTag(TestTags.SubmitButton).performClick()
        verify(exactly = 1) {
            onAddButtonClicked(any<String>())
        }
    }

    @Test
    fun clickOnDisabledSubmitButton_doesNothing() = runComposeTest {
        setupAddItemContent(isAddInProgress = true)

        onNodeWithTag(TestTags.SubmitButton).performClick()
        verify {
            onAddButtonClicked wasNot called
        }
    }

    private fun ComposeContentTestRule.setupAddItemContent(
        isAddInProgress: Boolean
    ) {
        setContent {
            AddItemContent(
                screenState = AddItemViewModel.ScreenState(isAddInProgress),
                onAddButtonClicked = onAddButtonClicked
            )
        }
    }
}
