package com.devstart.hymn.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devstart.hymn.home.repository.Repository
import com.devstart.hymn.api.ApiResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HymnViewModel @Inject constructor(private val hymnRepository: Repository) : ViewModel() {

    private val mutableHymnLiveData = MutableLiveData<ApiResponse>()

    fun getHymnData() : LiveData<ApiResponse> = mutableHymnLiveData
    fun getSearchData() : LiveData<ApiResponse> = hymnRepository.searchLiveData

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