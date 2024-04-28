package com.example.comicchallenge2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.comicchallenge2.model.Comic
import com.example.comicchallenge2.network.MarvelApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ComicViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val marvelApiService: MarvelApiService
) : ViewModel() {

    private val tag: String = "ComicViewModel"

    private val _comicData = MutableLiveData<Comic>()
    val comicData: LiveData<Comic> = _comicData

    init {
        getComic(id.toInt())
    }

    private fun getComic(comicId: Int) {
        Log.v(tag, "getComic: $comicId")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make network request to fetch comic data
                val response = marvelApiService.getComic(comicId)
                if (response.isSuccessful) {
                    val comic = response.body()

                    Log.v(tag, comic.toString())

                    comic?.data?.results?.get(0)?.let {
                        _comicData.postValue(it)
                    }

                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    @AssistedFactory
    interface ComicViewModelFactory {
        fun create(id: String): ComicViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: ComicViewModelFactory,
            noteId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(noteId) as T
            }
        }
    }


}
