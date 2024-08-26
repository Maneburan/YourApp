package com.example.data.di

import android.content.Context
import com.example.data.core.AuthToken
import com.example.data.core.Profile
import com.example.data.localDataSourceImp.AuthTokenLocal
import com.example.data.localDataSourceImp.ProfileLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
internal object LocalDataSourceModule {

    @Provides
    fun authTokenLocal(@ApplicationContext appContext: Context): AuthToken.LocalSource {
        return AuthTokenLocal(appContext)
    }

    @Provides
    fun profileLocal(@ApplicationContext appContext: Context): Profile.LocalSource {
        return ProfileLocal(appContext)
    }

}