package com.goliaeth.logintestapp.data.repository

import com.goliaeth.logintestapp.data.UserPreferences
import com.goliaeth.logintestapp.data.network.AuthAPI
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthAPI,
    private val preferences: UserPreferences
): BaseRepository(api) {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }
}