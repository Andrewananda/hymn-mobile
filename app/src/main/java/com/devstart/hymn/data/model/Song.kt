package com.devstart.hymn.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import com.squareup.moshi.Json

@Entity(tableName = "songs")
@Parcelize
data class Song(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "chorus") val chorus: String,
    @ColumnInfo(name = "song") val song: String,
    @ColumnInfo(name = "category_id") val category_id: Int,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "updated_at") val updated_at : String,
): Parcelable
