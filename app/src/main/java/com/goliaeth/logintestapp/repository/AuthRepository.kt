package com.goliaeth.logintestapp.repository

import com.goliaeth.logintestapp.network.AuthAPI

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