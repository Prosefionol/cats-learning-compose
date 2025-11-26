package com.example.catslearningcompose

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.junit4.MockKRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class ComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockKRule = MockKRule(this)

    protected inline fun runComposeTest(block: ComposeContentTestRule.() -> Unit) {
        with(composeTestRule) {
            block()
        }
    }
}
