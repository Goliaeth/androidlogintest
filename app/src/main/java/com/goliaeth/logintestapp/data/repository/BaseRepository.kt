package com.goliaeth.logintestapp.data.repository

import com.goliaeth.logintestapp.data.network.BaseAPI
import com.goliaeth.logintestapp.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseAPI): SafeApiCall {

    suspend fun logout() = safeApiCall {
        api.logout()
    }

}