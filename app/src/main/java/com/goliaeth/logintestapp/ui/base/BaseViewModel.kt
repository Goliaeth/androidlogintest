package com.goliaeth.logintestapp.ui.base

import androidx.lifecycle.ViewModel
import com.goliaeth.logintestapp.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout() = withContext(Dispatchers.IO) { repository.logout() }

}