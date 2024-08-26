package com.example.data.di

import com.example.data.repositoryImp.AuthTokenRepository
import com.example.data.repositoryImp.PhoneAuthRepository
import com.example.data.repositoryImp.ProfileRepository
import com.example.domain.core.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun authTokenRepository(authTokenRepository: AuthTokenRepository): Repository.AuthToken

    @Binds
    abstract fun phoneAuthRepository(phoneAuthRepository: PhoneAuthRepository): Repository.PhoneAuth

    @Binds
    abstract fun profileRepository(profileRepository: ProfileRepository): Repository.Profile

}