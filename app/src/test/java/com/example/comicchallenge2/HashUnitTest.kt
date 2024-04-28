package com.example.comicchallenge2

import com.example.comicchallenge2.network.AuthInterceptor
import org.junit.Test

import org.junit.Assert.*

/**
 *
 */
class HashUnitTest {

    @Test
    fun hashIsGood() {

        val authInterceptor = AuthInterceptor()

        val timestamp = 1234L

        val hash = authInterceptor.generateHash(timestamp)

        assertEquals(
            "Hash didn't equal what was expected",
            "25efa088d9a198969c6aeb5c803dad65",
            hash
        )
    }

}
