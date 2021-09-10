package com.devstart.hymn.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devstart.hymn.data.model.Song

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Query("SELECT * FROM songs WHERE title LIKE :query OR chorus LIKE :query OR song LIKE :query")
    fun querySongs(query: String): List<Song>

    @Query("SELECT * FROM songs")
    fun getAllSongs() : List<Song>
}