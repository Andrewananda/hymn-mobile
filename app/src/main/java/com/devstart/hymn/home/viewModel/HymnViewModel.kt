package com.devstart.hymn.home.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.devstart.hymn.home.repository.Repository
import com.devstart.hymn.api.ApiResponse
import com.devstart.hymn.api.Success
import com.devstart.hymn.home.workManager.UpdateDatabaseWorker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HymnViewModel @Inject constructor(private val hymnRepository: Repository) : ViewModel() {

    private val mutableHymnLiveData = MutableLiveData<ApiResponse>()
    private val mutableSearchResponse = MutableLiveData<ApiResponse>()


    fun getHymnData() : LiveData<ApiResponse> = mutableHymnLiveData
    fun getSearchData() : LiveData<ApiResponse> = mutableSearchResponse

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    init {
        coroutineScope.launch{
            getRepoData()
        }
    }

    private fun getRepoData () {
        coroutineScope.launch {
            hymnRepository.fetchHymns().collect {
                mutableHymnLiveData.postValue(it)
            }
        }
    }

    fun searchHymn(text: String?) {
        if (text != null) {
            if (text.isBlank()){
                getRepoData()
            }else{
                coroutineScope.launch {
                    hymnRepository.searchHymn(text).collect {
                        mutableSearchResponse.postValue(it)
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}