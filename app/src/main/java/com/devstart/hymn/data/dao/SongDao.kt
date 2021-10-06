package com.devstart.hymn.data.dao

import androidx.room.*
import com.devstart.hymn.data.model.Song
import com.devstart.hymn.data.model.SongResponse

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Query("SELECT * FROM song_response WHERE title LIKE '%' || :query || '%' OR chorus LIKE '%' || :query || '%' OR song LIKE '%' || :query || '%' OR number LIKE '%' || :query || '%' ")
    fun querySongs(query: String?): List<SongResponse>

    @Query("SELECT * FROM songs")
    fun getAllSongs() : List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongResponse(songs: List<SongResponse>)

    @Query("SELECT * FROM song_response ORDER BY id DESC")
    fun fetchAllSongs(): List<SongResponse>

}