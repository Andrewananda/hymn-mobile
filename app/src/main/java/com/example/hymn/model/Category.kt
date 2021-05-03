package com.example.hymn.model

import com.squareup.moshi.Json

data class Category(
   @Json(name = "id") val id: Int,
   @Json(name = "name") val name: String,
   @Json(name = "created_at") val created_at: String,
   @Json(name = "updated_at")val updated_at: String
)
