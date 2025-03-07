package com.omelan.cofi

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color.parseColor
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.omelan.cofi.model.Recipe
import com.omelan.cofi.model.RecipeViewModel
import com.omelan.cofi.model.StepsViewModel
import com.omelan.cofi.pages.RecipeDetails
import com.omelan.cofi.pages.RecipeEdit
import com.omelan.cofi.pages.RecipeList
import com.omelan.cofi.ui.CofiTheme
import com.omelan.cofi.utils.ScreenshotsHelpers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalAnimatedInsets
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@RunWith(JUnit4::class)
class ScreenshotCreator {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @SuppressLint("ComposableNaming")
    @Composable
    private fun setNavigationBarColor(darkMode: Boolean) {
        rememberSystemUiController().setNavigationBarColor(
            color = if (darkMode) Color(parseColor("#121212")) else Color.White,
            darkIcons = darkMode
        )
    }

    private fun saveScreenshot(name: String) {
        val screenShot = composeTestRule.onRoot().captureToImage().asAndroidBitmap()
        ScreenshotsHelpers.saveBitmap(
            context = composeTestRule.activity,
            bitmap = screenShot,
            format = Bitmap.CompressFormat.PNG,
            displayName = name,
            mimeType = "image/png"
        )
    }

    @Test
    fun recipeListScreenshot() {
        composeTestRule.setContent {
            CofiTheme(isDarkMode = false) {
                setNavigationBarColor(false)
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeList(
                        navigateToRecipe = {},
                        addNewRecipe = { },
                        goToSettings = { }
                    )
                }
            }
        }
        saveScreenshot("1_en-US")
    }

    @Test
    fun recipeListScreenshotDark() {
        composeTestRule.setContent {
            setNavigationBarColor(true)
            CofiTheme(isDarkMode = true) {
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeList(
                        navigateToRecipe = {},
                        addNewRecipe = { },
                        goToSettings = { }
                    )
                }
            }
        }
        saveScreenshot("2_en-US")
    }

    @Test
    fun recipeDetailsScreenshot() {
        composeTestRule.setContent {
            setNavigationBarColor(false)
            val stepsViewModel: StepsViewModel = viewModel()
            val recipeViewModel: RecipeViewModel = viewModel()

            CofiTheme(isDarkMode = false) {
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeDetails(
                        recipeId = 1,
                        recipeViewModel = recipeViewModel,
                        stepsViewModel = stepsViewModel
                    )
                }
            }
        }
        saveScreenshot("3_en-US")
    }

    @Test
    fun recipeDetailsScreenshotDark() {
        composeTestRule.setContent {
            setNavigationBarColor(true)
            val stepsViewModel: StepsViewModel = viewModel()
            val recipeViewModel: RecipeViewModel = viewModel()

            CofiTheme(isDarkMode = true) {
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeDetails(
                        recipeId = 1,
                        recipeViewModel = recipeViewModel,
                        stepsViewModel = stepsViewModel
                    )
                }
            }
        }
        saveScreenshot("4_en-US")
    }

    @Test
    fun recipeEditScreenshot() {
        composeTestRule.setContent {
            setNavigationBarColor(false)
            val recipeViewModel: RecipeViewModel = viewModel()
            val stepsViewModel: StepsViewModel = viewModel()
            val recipe = recipeViewModel.getRecipe(1)
                .observeAsState(Recipe(name = "", description = ""))
            val steps = stepsViewModel.getAllStepsForRecipe(1)
                .observeAsState(listOf())
            CofiTheme(isDarkMode = false) {
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeEdit(
                        saveRecipe = { _, _ -> },
                        recipeToEdit = recipe.value,
                        stepsToEdit = steps.value
                    )
                }
            }
        }
        saveScreenshot("5_en-US")
    }

    @Test
    fun recipeEditScreenshotDark() {
        composeTestRule.setContent {
            setNavigationBarColor(true)
            val recipeViewModel: RecipeViewModel = viewModel()
            val stepsViewModel: StepsViewModel = viewModel()
            val recipe = recipeViewModel.getRecipe(1)
                .observeAsState(Recipe(name = "", description = ""))
            val steps = stepsViewModel.getAllStepsForRecipe(1)
                .observeAsState(listOf())
            CofiTheme(isDarkMode = true) {
                CompositionLocalProvider(
                    LocalPiPState provides false,
                ) {
                    RecipeEdit(
                        saveRecipe = { _, _ -> },
                        recipeToEdit = recipe.value,
                        stepsToEdit = steps.value
                    )
                }
            }
        }
        saveScreenshot("6_en-US")
    }
}