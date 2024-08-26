package com.example.domain.useCaseImp

import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import com.example.domain.core.UseCase
import javax.inject.Inject

class PhoneAuth @Inject constructor(
    private val profileRepository: Repository.Profile,
    private val phoneAuthRepository: Repository.PhoneAuth,
): UseCase.PhoneAuth {

    @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
    override suspend fun sendAuthCode(phone: String): Boolean {
        return phoneAuthRepository.sendCode(phone)
    }

    @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
    override suspend fun authorize(phone: String, code: String): Entity.Profile? {
        val checkAuthCode = phoneAuthRepository.checkCode(phone, code)

        return if (checkAuthCode.isUserExists && checkAuthCode.authToken != null) {
            profileRepository.getMe(checkAuthCode.authToken.access)
        } else null
    }

}