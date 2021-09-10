package com.devstart.hymn.data.model

import com.squareup.moshi.Json

data class Response(
    @Json(name = "status_message") val status_message: String,
    @Json(name = "data") val data: List<SongResponse>,
    @Json(name = "errors") val errors: String?,
    @Json(name = "message") val message: String,
    @Json(name = "count") val count: Int
)
