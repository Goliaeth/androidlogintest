package com.goliaeth.logintestapp.data.repository

import com.goliaeth.logintestapp.data.UserPreferences
import com.goliaeth.logintestapp.data.network.AuthAPI

class AuthRepository(
    private val api: AuthAPI,
    private val preferences: UserPreferences
): BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}