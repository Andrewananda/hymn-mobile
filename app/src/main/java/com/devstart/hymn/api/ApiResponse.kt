package com.devstart.hymn.api

sealed class ApiResponse

data class Success<T>(val data: T): ApiResponse()

class Failure(val throwable: Throwable) : ApiResponse()
