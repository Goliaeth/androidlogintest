package com.goliaeth.logintestapp.ui.base

import androidx.lifecycle.ViewModel
import com.goliaeth.logintestapp.data.network.AuthAPI
import com.goliaeth.logintestapp.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(api: AuthAPI) = withContext(Dispatchers.IO) { repository.logout(api) }

}