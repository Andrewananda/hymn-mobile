package com.devstart.hymn.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.squareup.moshi.Json

@Parcelize
data class Song(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "number") val number: Int,
    @Json(name = "chorus") val chorus: String,
    @Json(name = "song") val song: String,
    @Json(name = "category_id") val category_id: Int,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at : String,
    @Json(name = "category") val category: Category
): Parcelable
