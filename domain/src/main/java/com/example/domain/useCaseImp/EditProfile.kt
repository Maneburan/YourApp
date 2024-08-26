package com.example.domain.useCaseImp

import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import com.example.domain.core.UseCase
import javax.inject.Inject

class EditProfile @Inject constructor(
    private val profileRepository: Repository.Profile,
    private val authTokenRepository: Repository.AuthToken,
): UseCase.EditProfile {

    override suspend fun editMe(editProfile: Entity.EditProfile) {
        var authToken = authTokenRepository.get() ?: throw Exceptions.Unauthorized()

        try {
            profileRepository.editMe(authToken.access, editProfile)
            profileRepository.getMe(authToken.access)
        } catch (unauthorized: Exceptions.Unauthorized) {
            authToken = authTokenRepository.refresh(authToken)
            profileRepository.editMe(authToken.access, editProfile)
        }
    }

}