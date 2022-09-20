package com.goliaeth.logintestapp.ui.auth

interface Factory<T> {
    fun create() : T
}