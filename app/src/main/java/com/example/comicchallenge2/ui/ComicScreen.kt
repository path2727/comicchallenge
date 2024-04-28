package com.example.comicchallenge2.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.comicchallenge2.model.Comic


// Main composable function for the comic book page

@Composable
internal fun ComicScreen(
    comic: Comic?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detail",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        ComicTitle(title = comic?.title ?: "")
        ComicDescription(description = comic?.description ?: "")

        Spacer(modifier = Modifier.height(20.dp))

        comic?.thumbnail?.path?.let {
            var imageUrl =
                (it) + "/" + "portrait_uncanny" + "." + (comic.thumbnail.extension ?: "")
            imageUrl = imageUrl.replace("http", "https")

            ComicCoverImage(
                imageUrl = imageUrl
            )
        }
    }
}


// Composable function to display comic book title
@Composable
fun ComicTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
}

// Composable function to display comic book description
@Composable
fun ComicDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

// Composable function to display comic book cover image
@Composable
fun ComicCoverImage(imageUrl: String) {
    Log.v("comicCoverImage", imageUrl)

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
    )

}

