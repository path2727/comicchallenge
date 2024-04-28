package com.example.comicchallenge2.network

import android.util.Log
import com.example.comicchallenge2.Keys
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * OKHTTP interceptor for the Marvel API authentication tokens.
 *
 * <p>
 * https://developer.marvel.com/documentation/authorization
 * </p>
 *
 * <p>
 * For example, a user with a public key of "1234" and a private key of "abcd" could
 * construct a valid call as follows:
 * http://gateway.marvel.com/v1/public/comics
 * ?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150
 * (the hash value is the md5 digest of 1abcd1234)
 * </p>
 */
class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url
        val ts = System.currentTimeMillis()

        Log.v("tag", "ts: $ts")
        Log.v("tag", "originalHttpUrl: $originalHttpUrl")

        // Generate hash.
        val hash = generateHash(ts)
        Log.v("hash", "hash $hash")

        // Append the global query paramters to all requests.
        val url: HttpUrl = originalHttpUrl.newBuilder().addQueryParameter("apikey", Keys.PUBLIC_KEY)
            .addQueryParameter("ts", ts.toString()).addQueryParameter("hash", hash).build()


        Log.v("tag", "url: $url")

        val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    fun generateHash(ts: Long): String? {
        val hash = ts.toString() + Keys.PRIVATE_KEY + Keys.PUBLIC_KEY
        return try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            val bytes: ByteArray = md.digest(hash.toByteArray())
            val sb = StringBuilder()
            for (b in bytes) {
                sb.append(String.format("%02x", b and 0xff.toByte()))
            }
            sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }


}