package com.example.data.di

import com.example.data.core.AuthToken
import com.example.data.core.PhoneAuth
import com.example.data.core.Profile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object RemoteDataSourceModule {

    @Provides
    fun authTokenRemote(@Retrofit1 retrofit: Retrofit): AuthToken.RemoteSource {
        return retrofit.create(AuthToken.RemoteSource::class.java)
    }

    @Provides
    fun phoneAuthRemote(@Retrofit1 retrofit: Retrofit): PhoneAuth.RemoteSource {
        return retrofit.create(PhoneAuth.RemoteSource::class.java)
    }

    @Provides
    fun profileRemote(@Retrofit1 retrofit: Retrofit): Profile.RemoteSource {
        return retrofit.create(Profile.RemoteSource::class.java)
    }

}

