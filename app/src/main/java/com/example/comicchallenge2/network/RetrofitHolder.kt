package com.example.comicchallenge2.network

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File

class RetrofitHolder(context: Context) {

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }

    private val tag: String = "RetrofitHolder"

    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // If the API adds new
        // fields in the future, we don't want the JSON parser to fail on them
        registerKotlinModule()
    }

    private val client: Retrofit

    val apiService: MarvelApiService

    init {
        val httpClient = OkHttpClient.Builder()

        // Add cache so that we don't go over our quota too often.
        httpClient.cache(
            Cache(
                directory = File(context.cacheDir, "http_cache"),
                maxSize = 50L * 1024L * 1024L // 50 MiB
            )
        )

        // Add logging interceptor, very useful when building a new application.
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        // cache logging
        httpClient.addInterceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            val response: Response = chain.proceed(chain.request())
            val cacheStr = when {
                response.cacheResponse != null -> "yes"
                response.networkResponse != null -> "no"
                else -> "no"
            }
            Log.v(tag, "was from cache: $cacheStr $url")
            response
        }


        // Add auth token interceptor so that we don't have to pass the tokens at the
        // application level.  the app developers won't have to
        // continually worry about adding tokens
        httpClient.addInterceptor(AuthInterceptor())


        // Better caching by removing query parameters that change every request (hash, ts).
        httpClient.addNetworkInterceptor(CacheHelperNetworkInterceptor())

        client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(httpClient.build())
            .build()

        apiService = client.create(MarvelApiService::class.java)
    }
}