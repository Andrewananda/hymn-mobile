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

    private val hymnLiveData = MutableLiveData<ApiResponse>()
             val getHymnLiveData: LiveData<ApiResponse>
            get() = hymnLiveData
    private val setSearchLiveData = MutableLiveData<ApiResponse>()
    val searchLiveData : LiveData<ApiResponse>
    get() = setSearchLiveData

     private suspend fun fetchFromApi() {
         Log.i("FetchingFromApi", "true")
        try {
            val hymns = apiService.getSongsAsync()
            val hymnData = hymns.await()
            hymnLiveData.postValue(Success(hymnData))
        } catch (t: Throwable) {
            Log.i("FetchingFromApiError", t.localizedMessage)
            hymnLiveData.postValue(Failure(t))
        }
    }

     suspend fun fetchHymns()  {
         try {
             val hymns = songDao.getAllSongs()
             if (!hymns.isNullOrEmpty()){
                 hymnLiveData.postValue(Success(hymns))
             }else{
                 fetchFromApi()
             }
         }catch (t: Throwable) {
             hymnLiveData.postValue(Failure(t))
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