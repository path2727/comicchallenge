package com.example.comicchallenge2.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comicchallenge2.model.Comic
import com.example.comicchallenge2.navigation.ScreenRoutes
import com.example.comicchallenge2.viewmodel.ComicListViewModel

@Composable
fun ComicListScreen(
    navController: NavHostController,
    viewModel: ComicListViewModel = hiltViewModel()
) {

    val comics by viewModel.comicsData.observeAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "List",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            comics?.let {
                itemsIndexed(it) { index, _ ->
                    ComicForList(it[index]) { comic ->
                        Log.v("comiclistscreen", "comic selected: ${comic.id}")
                        navController.navigate(ScreenRoutes.COMIC_DETAIL_SCREEN + "?id=${comic.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun ComicForList(
    comic: Comic,
    onSelectComic: (Comic) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(85.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                onSelectComic.invoke(comic)
            })
    ) {
        Text(
            text = comic.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
    }

}