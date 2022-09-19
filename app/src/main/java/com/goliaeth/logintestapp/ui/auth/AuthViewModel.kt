package com.goliaeth.logintestapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.repository.AuthRepository
import com.goliaeth.logintestapp.data.responces.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(email, password)
    }
}