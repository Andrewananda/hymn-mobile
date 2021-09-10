package com.devstart.hymn.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "categories")
@Parcelize
data class Category(
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id") val id: Int,
   @ColumnInfo(name = "name") val name: String,
   @ColumnInfo(name = "created_at") val created_at: String,
   @ColumnInfo(name = "updated_at")val updated_at: String
) : Parcelable
