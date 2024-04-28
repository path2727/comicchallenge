package com.example.comicchallenge2.model.response

import com.example.comicchallenge2.model.Comic


@Suppress("unused")
class ComicData {

    var offset: Long? = null
    var limit: Long? = null
    var total: Long? = null
    var count: Long? = null
    var results: List<Comic>? = null

}
