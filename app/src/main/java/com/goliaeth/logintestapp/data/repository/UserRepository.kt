package com.goliaeth.logintestapp.data.repository

import com.goliaeth.logintestapp.data.UserPreferences
import com.goliaeth.logintestapp.data.network.AuthAPI
import com.goliaeth.logintestapp.data.network.UserAPI

class UserRepository(
    private val api: UserAPI
): BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}