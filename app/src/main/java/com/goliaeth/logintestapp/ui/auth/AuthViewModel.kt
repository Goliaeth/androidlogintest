package com.goliaeth.logintestapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.repository.AuthRepository
import com.goliaeth.logintestapp.data.responces.LoginResponse
import com.goliaeth.logintestapp.data.responces.LoginResponse2
import com.goliaeth.logintestapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse2>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse2>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        repository.saveAccessTokens(accessToken, refreshToken)
    }
}