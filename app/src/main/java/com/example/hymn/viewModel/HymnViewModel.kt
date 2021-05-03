package com.example.hymn.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hymn.repository.Repository
import com.example.hymn.api.ApiResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class HymnViewModel @Inject constructor(private val hymnRepository: Repository) : ViewModel() {

    private val hymnLiveData = MutableLiveData<ApiResponse>()

    fun getHymnData() : LiveData<ApiResponse> = hymnLiveData

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())


    init {
        coroutineScope.launch{
            getRepoData()
        }
    }

    private suspend fun getRepoData () {
        hymnRepository.fetchHymns()
        hymnLiveData.postValue(hymnRepository.getHymnLiveData.value)
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}