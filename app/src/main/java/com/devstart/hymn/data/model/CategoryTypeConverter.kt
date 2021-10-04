package com.devstart.hymn.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryTypeConverter {
    @TypeConverter
    fun categoryToString(item: Category?): String? {
        val type = object : TypeToken<Category>() {}.type
        return Gson().toJson(item, type)
    }

    @TypeConverter
    fun toCategory(json: String): Category {
        val type = object : TypeToken<Category>() {}.type
        return Gson().fromJson(json, type)
    }
}