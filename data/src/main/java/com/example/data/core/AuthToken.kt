package com.example.data.core

import com.example.domain.core.Entity
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal object AuthToken {

    interface Mapper {
        fun toRefreshTokenRequest(refreshToken: String): RemoteSource.Model.Request.RefreshToken
        fun toAuthTokenLocal(authTokenEntity: Entity.AuthToken): LocalSource.Model.AuthToken
        fun toAuthTokenEntity(access: String, refresh: String): Entity.AuthToken
    }

    interface LocalSource {
        fun get(): Flow<Model.AuthToken?>
        suspend fun remove()
        suspend fun save(authToken: Model.AuthToken)

        object Model {
            data class AuthToken(
                val access: String,
                val refresh: String,
            )
        }
    }

    interface RemoteSource {
        @POST("/api/v1/users/refresh-token/") suspend
        fun refreshToken(@Header("Authorization") authorization: String,
                         @Body refreshToken: Model.Request.RefreshToken
        ): Model.Response.AuthToken

        object Model {
            object Request {
                data class RefreshToken(
                    @Json(name = "refresh_token") val refreshToken: String
                )
            }

            object Response {
                data class AuthToken(
                    @Json(name = "refresh_token") val refreshToken: String?,
                    @Json(name = "access_token") val accessToken: String?,
                )
            }
        }
    }

}