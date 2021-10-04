package com.devstart.hymn.home.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devstart.hymn.api.ApiResponse
import com.devstart.hymn.data.api.ApiService
import com.devstart.hymn.api.Failure
import com.devstart.hymn.api.Success
import com.devstart.hymn.data.dao.SongDao
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService, private val songDao: SongDao) {

    private val setSearchLiveData = MutableLiveData<ApiResponse>()
    val searchLiveData : LiveData<ApiResponse>
    get() = setSearchLiveData

     private suspend fun fetchFromApi() = flow {
        try {
            val hymns = apiService.getSongsAsync()
            val hymnData = hymns.await()
            songDao.insertSongResponse(hymnData.data)
            emit(Success(songDao.fetchAllSongs()))
        } catch (t: Throwable) {
            Log.i("FetchingFromApiError", t.localizedMessage)
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

     suspend fun searchHymn(text: String) {
        search(text)
    }

    private suspend fun search(text: String) {

        val query = apiService.queryHymnsAsync(text)
        try {
            val response = query.await()
            setSearchLiveData.value = Success(response)
        } catch (t: Throwable) {
            setSearchLiveData.value = Failure(t)
        }
    }

}