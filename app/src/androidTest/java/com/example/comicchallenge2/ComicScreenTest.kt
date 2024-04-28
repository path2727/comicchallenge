package com.example.comicchallenge2

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.comicchallenge2.model.Comic
import com.example.comicchallenge2.ui.ComicScreen
import org.junit.Rule
import org.junit.Test

class ComicScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()


    @Test
    fun testCommicScreen() {
        val comic = Comic()
        comic.title = "Test Title"
        comic.description = "Test Description"
        composeTestRule.setContent {
            ComicScreen(comic)
        }
        composeTestRule.onNodeWithText("Detail").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
    }
}