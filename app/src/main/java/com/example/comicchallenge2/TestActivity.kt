package com.example.comicchallenge2


import androidx.activity.ComponentActivity
import com.example.comicchallenge2.viewmodel.ComicViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun comicViewModelFactory(): ComicViewModel.ComicViewModelFactory
    }

}
