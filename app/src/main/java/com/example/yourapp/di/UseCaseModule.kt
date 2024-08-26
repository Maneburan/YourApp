package com.example.yourapp.di

import com.example.domain.core.UseCase
import com.example.domain.useCaseImp.EditProfile
import com.example.domain.useCaseImp.GetProfile
import com.example.domain.useCaseImp.LogOut
import com.example.domain.useCaseImp.PhoneAuth
import com.example.domain.useCaseImp.PhoneRegistration
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun editProfileUseCase(editProfile: EditProfile): UseCase.EditProfile

    @Binds
    @ViewModelScoped
    abstract fun getProfileUseCase(getProfile: GetProfile): UseCase.GetProfile

    @Binds
    @ViewModelScoped
    abstract fun logOutUseCase(logOut: LogOut): UseCase.LogOut

    @Binds
    @ViewModelScoped
    abstract fun phoneAuthUseCase(phoneAuth: PhoneAuth): UseCase.PhoneAuth

    @Binds
    @ViewModelScoped
    abstract fun phoneAuthRegistrationUseCase(phoneAuthRegistrationImp: PhoneRegistration): UseCase
        .PhoneRegistration

}