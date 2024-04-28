package com.example.comicchallenge2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicchallenge2.model.Comic
import com.example.comicchallenge2.model.response.ComicsResponse
import com.example.comicchallenge2.network.MarvelApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject


@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val marvelApiService: MarvelApiService
) : ViewModel() {

    private val tag = "ComicListViewModel"

    private val _comicsData = MutableLiveData<List<Comic>>()
    val comicsData: LiveData<List<Comic>> = _comicsData

    init {
        Log.v(tag, "init")
        getComics()
    }

    private fun getComics() {
        Log.v(tag, "getComics")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make network request to fetch comic data
                val response = marvelApiService.getComics(500, 100)

                if (response.isSuccessful) {
                    val comic: ComicsResponse? = response.body()

                    val list: List<Comic>? = comic?.data?.results

                    list?.let {
                        _comicsData.postValue(it)
                    }

                } else {
                    // Handle error
                    val rawResponse: Response? = response.raw()
                    Log.e(tag, rawResponse?.code.toString())
                    Log.e(tag, response.message())
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e(tag, e.message.toString(), e)
            }
        }
    }
}
