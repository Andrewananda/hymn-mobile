package com.devstart.hymn.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devstart.hymn.home.repository.Repository
import com.devstart.hymn.api.ApiResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class HymnViewModel @Inject constructor(private val hymnRepository: Repository) : ViewModel() {

    private val hymnLiveData = MutableLiveData<ApiResponse>()

    fun getHymnData() : LiveData<ApiResponse> = hymnLiveData
    fun getSearchData() : LiveData<ApiResponse> = hymnRepository.searchLiveData

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    init {
        coroutineScope.launch{
            getRepoData()
        }
    }

    private fun getRepoData () {
        coroutineScope.launch {
            hymnRepository.fetchHymns()
            hymnLiveData.postValue(hymnRepository.getHymnLiveData.value)
        }
    }

    fun searchHymn(text: String) {
        coroutineScope.launch {
            hymnRepository.searchHymn(text)
        }
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}