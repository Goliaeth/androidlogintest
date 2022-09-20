package com.goliaeth.logintestapp.data.network

import com.goliaeth.logintestapp.data.responces.LoginResponse
import com.goliaeth.logintestapp.data.responces.LoginResponse2
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPI: BaseAPI {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse2

}