package com.example.comicchallenge2.modules

import android.content.Context
import com.example.comicchallenge2.network.MarvelApiService
import com.example.comicchallenge2.network.RetrofitHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
class NetworkModule {

    @Provides
    @Singleton
    fun provideMarvelApiService(@ApplicationContext context: Context): MarvelApiService {

        val holder = RetrofitHolder(context)

        return holder.apiService

    }
}
