package com.example.domain.core

import com.example.domain.Exceptions
import kotlinx.coroutines.flow.Flow

object Repository {

    interface AuthToken {
        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun refresh(authTokenEntity: Entity.AuthToken): Entity.AuthToken

        suspend fun get(): Entity.AuthToken?
        suspend fun remove()
        suspend fun save(authTokenEntity: Entity.AuthToken)
    }

    interface PhoneAuth {
        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun sendCode(phone: String) : Boolean

        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun checkCode(phone: String, code: String): Entity.CheckAuthCode

        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun register(phone: String, name: String, username: String): Entity.AuthToken
    }

    interface Profile {
        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class, Exceptions.Unauthorized::class)
        suspend fun editMe(accessToken: String, editProfileEntity: Entity.EditProfile)

        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class, Exceptions.Unauthorized::class)
        suspend fun getMe(accessToken: String): Entity.Profile

        fun getMeFlow(): Flow<Entity.Profile?>
        suspend fun removeMe()
    }

}