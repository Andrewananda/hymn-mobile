package com.example.hymn.api

import com.example.hymn.model.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("songs")
    fun getSongsAsync() : Deferred<Response>
}