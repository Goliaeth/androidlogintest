package com.goliaeth.logintestapp.data.network

import okhttp3.ResponseBody
import retrofit2.http.POST

interface BaseAPI {

    @POST("logout")
    suspend fun logout(): ResponseBody

}