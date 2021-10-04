package com.devstart.hymn.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devstart.hymn.data.dao.CategoryDao
import com.devstart.hymn.data.dao.SongDao
import com.devstart.hymn.data.model.Category
import com.devstart.hymn.data.model.CategoryTypeConverter
import com.devstart.hymn.data.model.Song
import com.devstart.hymn.data.model.SongResponse

@Database(entities = [Song::class, Category::class, SongResponse::class], version = 2, exportSchema = false)
@TypeConverters(CategoryTypeConverter::class)
abstract class HymnDatabase : RoomDatabase() {
    abstract fun songDao() : SongDao
    abstract fun categoryDao(): CategoryDao
}