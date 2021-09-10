package com.devstart.hymn.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "categories")
data class Category(
   @Json(name = "id") val id: Int,
   @Json(name = "name") val name: String,
   @Json(name = "created_at") val created_at: String,
   @Json(name = "updated_at")val updated_at: String
) : Parcelable
