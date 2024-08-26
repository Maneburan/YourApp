package com.example.data.core

import com.example.domain.core.Entity
import com.squareup.moshi.Json
import retrofit2.http.Body
import retrofit2.http.POST

internal object PhoneAuth {

    interface Mapper {
        fun toSendAuthCodeRequest(phone: String): RemoteSource.Model.Request.SendAuthCode
        fun toCheckAuthCodeRequest(phone: String, code: String): RemoteSource.Model.Request.CheckAuthCode
        fun toAuthTokenEntity(access: String, refresh: String): Entity.AuthToken

        @Throws(NullPointerException::class)
        fun toCheckAuthCodeEntity(checkAuthCodeResponse: RemoteSource.Model.Response.CheckAuthCode)
        : Entity.CheckAuthCode
        fun toRegisterRequest(phone: String, name: String, username: String)
        : RemoteSource.Model.Request.Register
    }

    interface RemoteSource {
        @POST("/api/v1/users/send-auth-code/")
        suspend fun sendAuthCode(@Body sendAuthCodeRequest: Model.Request.SendAuthCode)
        : Model.Response.SendAuthCode

        @POST("/api/v1/users/check-auth-code/")
        suspend fun checkAuthCode(@Body checkAuthCodeRequest: Model.Request.CheckAuthCode)
        : Model.Response.CheckAuthCode

        @POST("/api/v1/users/register/")
        suspend fun register(@Body registerRequest: Model.Request.Register): Model.Response.Register

        object Model {
            object Request {
                data class SendAuthCode(
                    val phone: String
                )
                data class CheckAuthCode(
                    val phone: String,
                    val code: String,
                )
                data class Register(
                    val phone: String,
                    val name: String,
                    val username: String,
                )
            }

            object Response {
                data class SendAuthCode(
                    @Json(name = "is_success") val isSuccess: Boolean?,
                )
                data class CheckAuthCode(
                    @Json(name = "refresh_token") val refreshToken: String?,
                    @Json(name = "access_token") val accessToken: String?,
                    @Json(name = "user_id") val userId: Int?,
                    @Json(name = "is_user_exists") val isUserExists: Boolean?,
                )
                data class Register(
                    @Json(name = "refresh_token") val refreshToken: String?,
                    @Json(name = "access_token") val accessToken: String?,
                    @Json(name = "user_id") val userId: Int?,
                )
            }
        }

    }

}