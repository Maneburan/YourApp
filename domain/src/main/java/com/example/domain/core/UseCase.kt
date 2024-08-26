package com.example.domain.core

import com.example.domain.Exceptions
import kotlinx.coroutines.flow.Flow

object UseCase {

    interface EditProfile {
        @Throws(
            Exceptions.Incorrect::class, Exceptions.HttpException::class,
            Exceptions.Unauthorized::class)
        suspend fun editMe(editProfile: Entity.EditProfile)
    }

    interface GetProfile {
        fun getFlow(): Flow<Entity.Profile?>

        @Throws(
            Exceptions.Incorrect::class, Exceptions.HttpException::class,
            Exceptions.Unauthorized::class)
        suspend fun get(): Entity.Profile
    }

    interface LogOut {
        suspend fun logOut()
    }

    interface PhoneAuth {
        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun sendAuthCode(phone: String) : Boolean

        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class)
        suspend fun authorize(phone: String, code: String): Entity.Profile?
    }

    interface PhoneRegistration {
        @Throws(Exceptions.Incorrect::class, Exceptions.HttpException::class) suspend
        fun register(phone: String, name: String, username: String): Entity.Profile
    }

}