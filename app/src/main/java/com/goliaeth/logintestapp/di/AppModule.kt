package com.goliaeth.logintestapp.di

import android.content.Context
import com.goliaeth.logintestapp.data.network.AuthAPI
import com.goliaeth.logintestapp.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthApi(
        @ApplicationContext context: Context,
        remoteDataSource: RemoteDataSource
    ): AuthAPI {
        return remoteDataSource.buildAPI(AuthAPI::class.java, context)
    }

}