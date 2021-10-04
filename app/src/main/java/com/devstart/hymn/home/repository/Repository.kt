package com.devstart.hymn.home.repository

import com.devstart.hymn.data.api.ApiService
import com.devstart.hymn.api.Failure
import com.devstart.hymn.api.Success
import com.devstart.hymn.data.dao.SongDao
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService, private val songDao: SongDao) {

     private suspend fun fetchFromApi() = flow {
        try {
            val hymns = apiService.getSongsAsync()
            val hymnData = hymns.await()
            songDao.insertSongResponse(hymnData.data)
            emit(Success(songDao.fetchAllSongs()))
        } catch (t: Throwable) {
            emit(Failure(t))
        }
    }

     suspend fun fetchHymns() = flow  {
         try {
             val hymns = songDao.fetchAllSongs()
             if (!hymns.isNullOrEmpty()){
                 emit(Success(hymns))
             }else{
                 fetchFromApi().collect {
                     emit(it)
                 }
             }
         }catch (t: Throwable) {
             emit(Failure(t))
         }
    }

      fun searchHymn(text: String?) = flow {
        try {
            val query = songDao.querySongs(text)
            emit(Success(query))
        }catch (t: Throwable) {
            emit(Failure(t))
        }
    }

}