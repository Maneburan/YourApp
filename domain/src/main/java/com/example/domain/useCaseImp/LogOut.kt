package com.example.domain.useCaseImp

import com.example.domain.core.Repository
import com.example.domain.core.UseCase
import javax.inject.Inject

class LogOut @Inject constructor(
    private val profileRepository: Repository.Profile,
    private val authTokenRepository: Repository.AuthToken,
): UseCase.LogOut {

    override suspend fun logOut() {
        profileRepository.removeMe()
        authTokenRepository.remove()
    }

}