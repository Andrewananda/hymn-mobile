package com.devstart.hymn.data.api

import com.devstart.hymn.data.model.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("songs")
    fun getSongsAsync() : Deferred<Response>

    @FormUrlEncoded
    @POST("search")
    fun queryHymnsAsync(
            @Field("value") title: String
    ) : Deferred<Response>
}