package com.goliaeth.logintestapp.data.repository

import com.goliaeth.logintestapp.data.network.AuthAPI

class AuthRepository(
    private val api: AuthAPI
): BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }
}