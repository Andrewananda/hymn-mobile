package com.devstart.hymn.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
   @Json(name = "id") val id: Int,
   @Json(name = "name") val name: String,
   @Json(name = "created_at") val created_at: String,
   @Json(name = "updated_at")val updated_at: String
) : Parcelable
