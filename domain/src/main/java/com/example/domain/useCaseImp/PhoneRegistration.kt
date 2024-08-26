package com.example.domain.useCaseImp

import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import com.example.domain.core.UseCase
import javax.inject.Inject

class PhoneRegistration @Inject constructor(
    private val profileRepository: Repository.Profile,
    private val poneAuthRepository: Repository.PhoneAuth,
): UseCase.PhoneRegistration {

    @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
    override suspend fun register(phone: String, name: String, username: String): Entity.Profile {
        val authToken = poneAuthRepository.register(phone, name, username)
        return profileRepository.getMe(authToken.access)
    }

}