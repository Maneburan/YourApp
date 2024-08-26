package com.example.data.repositoryImp

import com.example.data.core.PhoneAuth.Mapper
import com.example.data.core.PhoneAuth.RemoteSource
import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import retrofit2.HttpException
import javax.inject.Inject

internal class PhoneAuthRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val mapper: Mapper,
    private val authTokenRepository: Repository.AuthToken,
)
    : Repository.PhoneAuth {

    override suspend fun sendCode(phone: String): Boolean {
        try {
            val model = remoteSource.sendAuthCode(mapper.toSendAuthCodeRequest(phone))
            if (model.isSuccess == null) throw Exceptions.Incorrect()
            return model.isSuccess
        } catch (httpException: HttpException) {
            throw Exceptions.HttpException(httpException.message(), httpException.code())
        }
    }

    override suspend fun checkCode(phone: String, code: String): Entity.CheckAuthCode {
        try {
            val model = remoteSource.checkAuthCode(mapper.toCheckAuthCodeRequest(phone, code))
            if (model.isUserExists == null) throw Exceptions.Incorrect()

            if (model.isUserExists) {
                if (model.accessToken == null || model.refreshToken == null || model.userId == null)
                    throw Exceptions.Incorrect()

                authTokenRepository.save(mapper.toAuthTokenEntity(
                    access = model.accessToken,
                    refresh = model.refreshToken
                ))
            }

            return mapper.toCheckAuthCodeEntity(model)
        } catch (httpException: HttpException) {
            throw Exceptions.HttpException(httpException.message(), httpException.code())
        }
    }

    override suspend fun register(phone: String, name: String, username: String): Entity.AuthToken {
        try {
            val model = remoteSource.register(mapper.toRegisterRequest(phone, name, username))

            if (model.accessToken == null || model.refreshToken == null ||
                model.userId == null) throw Exceptions.Incorrect()

            authTokenRepository.save(mapper.toAuthTokenEntity(
                access = model.accessToken,
                refresh = model.refreshToken
            ))

            return mapper.toAuthTokenEntity(model.accessToken, model.refreshToken)
        } catch (httpException: HttpException) {
            throw Exceptions.HttpException(httpException.message(), httpException.code())
        }
    }
}