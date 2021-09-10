package com.devstart.hymn.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devstart.hymn.data.dao.CategoryDao
import com.devstart.hymn.data.dao.SongDao
import com.devstart.hymn.data.model.Category
import com.devstart.hymn.data.model.Song

@Database(entities = [Song::class, Category::class], version = 1, exportSchema = false)
abstract class HymnDatabase : RoomDatabase() {
    abstract fun songDao() : SongDao
    abstract fun categoryDao(): CategoryDao
}