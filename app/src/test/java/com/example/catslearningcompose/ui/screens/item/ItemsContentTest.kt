package com.example.catslearningcompose.ui.screens.item

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import com.example.catslearningcompose.ComposeTest
import com.example.catslearningcompose.TestTags
import com.example.catslearningcompose.model.LoadResult
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test

class ItemsContentTest: ComposeTest() {
    @RelaxedMockK
    private lateinit var onItemClicked: (Int) -> Unit

    @Test
    fun clickOnLazyColumn_executesNavigationAction() = runComposeTest {
        setupSuccessItemsContent()

        onAllNodes(hasTestTag(TestTags.LazyColumnItem)).onFirst().performClick()
        verify(exactly = 1) {
            onItemClicked(any<Int>())
        }
    }

    private fun ComposeContentTestRule.setupSuccessItemsContent() {
        setContent {
            ItemsContent(
                getLoadResult = {
                    LoadResult.Success(
                        ItemsViewModel.ScreenState(
                            listOf("Item 1", "Item 2")
                        )
                    )
                },
                onItemClicked = onItemClicked
            )
        }
    }
}
