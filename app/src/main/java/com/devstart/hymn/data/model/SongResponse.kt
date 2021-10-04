package com.devstart.hymn.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "song_response")
@Parcelize
data class SongResponse(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int,
    @ColumnInfo(name = "title")
    @Json(name = "title") val title: String,
    @ColumnInfo(name = "number")
    @Json(name = "number") val number: Int,
    @ColumnInfo(name = "chorus")
    @Json(name = "chorus") val chorus: String,
    @ColumnInfo(name = "song")
    @Json(name = "song") val song: String,
    @ColumnInfo(name = "category_id")
    @Json(name = "category_id") val category_id: Int,
    @ColumnInfo(name = "created_at")
    @Json(name = "created_at") val created_at: String,
    @ColumnInfo(name = "updated_at")
    @Json(name = "updated_at") val updated_at : String,
    @TypeConverters(CategoryTypeConverter::class) val category: Category?
): Parcelable