package com.example.comicchallenge2.network

import android.util.Log
import okhttp3.Interceptor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal class CacheHelperNetworkInterceptor : Interceptor {

    private val tag: String = "CacheHelperInterceptor"

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        Log.v(tag, "cacheHelperInterceptor")


        val originalResponse = chain.proceed(chain.request())

        val request = originalResponse.request
        Log.v(tag, "original: ${request.url}")

        val httpUrl = request.url.newBuilder()
            .removeAllQueryParameters("hash")
            .removeAllQueryParameters("ts")
            .removeAllQueryParameters("apikey")
            .build()
        Log.v(tag, "newUrl: ${httpUrl.toUrl()}")

        val newRequest = request.newBuilder()
            .url(httpUrl)
            .build()

        return originalResponse.newBuilder()
            .removeHeader("Cache-Control")
            .removeHeader("Expires")
            .request(newRequest)
            .header(
                "Cache-Control",
                String.format(Locale.US, "max-age=%d, max-stale=%d", CACHE_MAX_AGE, CACHE_MAX_STALE)
            )
            .header(
                "Expires",
                format.format(Date(System.currentTimeMillis() + CACHE_EXPIRES_OFFSET))
            )
            .build()
    }

    companion object {

        private val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)

        init {
            // the server responds in GMT so for easy comparisons we should use GMT
            format.timeZone = TimeZone.getTimeZone("GMT")
        }

        private const val CACHE_MAX_AGE = 60 * 60 // 1 hour
        private const val CACHE_MAX_STALE = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
        private const val CACHE_EXPIRES_OFFSET = 1000 * 60 * 60 * 2
    }
}
