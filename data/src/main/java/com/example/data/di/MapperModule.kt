package com.example.data.di

import com.example.data.core.AuthToken
import com.example.data.core.PhoneAuth
import com.example.data.core.Profile
import com.example.data.mapperImp.AuthTokenMapper
import com.example.data.mapperImp.PhoneAuthMapper
import com.example.data.mapperImp.ProfileMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class MapperModule {

    @Binds
    abstract fun authTokenMapper(authTokenMapper: AuthTokenMapper): AuthToken.Mapper

    @Binds
    abstract fun phoneAuthMapper(phoneAuthMapper: PhoneAuthMapper): PhoneAuth.Mapper

    @Binds
    abstract fun profileMapper(profileMapper: ProfileMapper): Profile.Mapper

}