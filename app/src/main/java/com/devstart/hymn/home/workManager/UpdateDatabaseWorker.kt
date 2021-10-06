package com.devstart.hymn.home.workManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.devstart.hymn.api.Failure
import com.devstart.hymn.api.Success
import com.devstart.hymn.data.api.ApiService
import com.devstart.hymn.data.dao.SongDao
import com.devstart.hymn.data.model.Response
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

@HiltWorker
class UpdateDatabaseWorker @AssistedInject constructor(@Assisted context: Context, @Assisted workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://hymn.devstart.co.ke/api/")
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        return try {
            apiService.getSongs().enqueue(object : Callback<Response>{
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    when(response){
                        is Success<*>-> {

                        }
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.e("NetworkWorkerFailure", t.localizedMessage)
                }

            })
            return Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }
}