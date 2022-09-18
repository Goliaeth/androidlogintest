package com.goliaeth.logintestapp.network

import com.goliaeth.logintestapp.responces.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPI {

    @FormUrlEncoded
    @POST("login")

    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse



}