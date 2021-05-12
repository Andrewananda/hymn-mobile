package com.devstart.hymn.api

import com.devstart.hymn.model.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("songs")
    fun getSongsAsync() : Deferred<Response>

    @FormUrlEncoded
    @POST("search_hymn")
    fun queryHymnsAsync(
            @Field("title") title: String
    ) : Deferred<Response>
}