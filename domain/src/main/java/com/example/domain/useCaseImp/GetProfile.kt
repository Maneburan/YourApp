package com.example.domain.useCaseImp

import android.util.Log
import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import com.example.domain.core.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val profileRepository: Repository.Profile,
    private val authTokenRepository: Repository.AuthToken,
): UseCase.GetProfile {

    override fun getFlow(): Flow<Entity.Profile?> {
        return profileRepository.getMeFlow()
    }

    @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class,
        Exceptions.Unauthorized::class)
    override suspend fun get(): Entity.Profile {
        var authToken = authTokenRepository.get() ?: throw Exceptions.Unauthorized()

        return try {
            profileRepository.getMe(authToken.access)
        } catch (unauthorized: Exceptions.Unauthorized) {
            Log.e("LODKA", "old authToken: $authToken")
            authToken = authTokenRepository.refresh(authToken)
            Log.e("LODKA", "new authToken: $authToken")
            profileRepository.getMe(authToken.access)
        }
    }

}