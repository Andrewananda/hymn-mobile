package com.example.hymn.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hymn.api.ApiResponse
import com.example.hymn.api.ApiService
import com.example.hymn.api.Failure
import com.example.hymn.api.Success
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    private val hymnLiveData = MutableLiveData<ApiResponse>()
             val getHymnLiveData: LiveData<ApiResponse>
            get() = hymnLiveData

     private suspend fun fetchFromApi() {
        try {
            val hymns = apiService.getSongsAsync()
            val hymnData = hymns.await()
             hymnLiveData.value = Success(hymnData)
        } catch (t: Throwable) {
            hymnLiveData.value = Failure(t)
        }
    }

    suspend fun fetchHymns() {
        fetchFromApi()
    }

     suspend fun searchHymn(text: String) {
        search(text)
    }

    private suspend fun search(text: String) {

        val query = apiService.queryHymnsAsync(text)
        try {
            val response = query.await()
            hymnLiveData.value = Success(response)
        } catch (t: Throwable) {
            hymnLiveData.value = Failure(t)
        }
    }

}