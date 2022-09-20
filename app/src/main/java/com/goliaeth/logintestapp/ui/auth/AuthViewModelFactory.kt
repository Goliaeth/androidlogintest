package com.goliaeth.logintestapp.ui.auth

import com.goliaeth.logintestapp.data.repository.AuthRepository

class AuthViewModelFactory(
    private val authRepository: AuthRepository
) : Factory<AuthViewModel> {
    override fun create(): AuthViewModel {
        return AuthViewModel(authRepository)
    }
}