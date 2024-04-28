package com.example.comicchallenge2.network

import com.example.comicchallenge2.model.response.ComicResponse
import com.example.comicchallenge2.model.response.ComicsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {

    @GET("comics")
    suspend fun getComics(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ComicsResponse>

    @GET("comics/{comicId}")
    suspend fun getComic(@Path("comicId") comicId: Int): Response<ComicResponse>

}
