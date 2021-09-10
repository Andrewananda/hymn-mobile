package com.devstart.hymn.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import com.squareup.moshi.Json

@Parcelize
@Entity(tableName = "songs")
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
