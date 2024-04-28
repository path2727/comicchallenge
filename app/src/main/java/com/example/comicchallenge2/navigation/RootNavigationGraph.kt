package com.example.comicchallenge2.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.comicchallenge2.MainActivity
import com.example.comicchallenge2.ui.ComicListScreen
import com.example.comicchallenge2.ui.ComicScreen
import com.example.comicchallenge2.viewmodel.ComicViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun RootNavigationGraph(
    navControl: NavHostController
) {
    NavHost(
        navController = navControl,
        startDestination = ScreenRoutes.LIST_SCREEN
    ) {
        composable(ScreenRoutes.LIST_SCREEN) {
            ComicListScreen(navController = navControl)
        }
        composable(
            ScreenRoutes.COMIC_DETAIL_SCREEN + "?id={comicId}",
            arguments = listOf(navArgument("comicId") { defaultValue = "" })
        ) { backStackEntry ->

            val comicId = backStackEntry.arguments?.getString("comicId").orEmpty()
            val viewModelFactory = EntryPointAccessors.fromActivity(
                activity = LocalContext.current as Activity,
                entryPoint = MainActivity.ViewModelFactoryProvider::class.java
            ).comicViewModelFactory()

            val viewModel: ComicViewModel = viewModel(
                factory = ComicViewModel.provideFactory(viewModelFactory, comicId)
            )


            val comic by viewModel.comicData.observeAsState(initial = null)

            ComicScreen(comic)
        }
    }
}

