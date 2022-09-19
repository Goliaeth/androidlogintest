package com.goliaeth.logintestapp.data.network

import com.goliaeth.logintestapp.data.responces.UserResponse
import retrofit2.http.GET

interface UserAPI {

    @GET("users/2")
    suspend fun getUser(): UserResponse
}