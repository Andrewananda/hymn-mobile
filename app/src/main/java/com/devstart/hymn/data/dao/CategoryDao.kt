package com.devstart.hymn.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.devstart.hymn.data.model.Category

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)
}