package com.example.data.core

import com.example.domain.core.Entity
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

internal object Profile {

    interface Mapper {
        fun toProfileRequest(editProfile: Entity.EditProfile): RemoteSource.Model.Request.Profile

        @Throws(NullPointerException::class)
        fun toProfileEntity(profileData: RemoteSource.Model.Response.Profile.ProfileData): Entity.Profile
    }

    interface RemoteSource {
        @GET("/api/v1/users/me/")
        suspend fun getMe(@Header("Authorization") authorization: String): Model.Response.Profile

        @PUT("/api/v1/users/me/")
        suspend
        fun editMe(@Header("Authorization") authorization: String,
                   @Body profileRequest: Model.Request.Profile
        ): Model.Response.Profile.ProfileData

        object Model {
            object Request {
                data class Profile(
                    val name: String?,
                    val username: String,
                    val birthday: Long?,
                    val city: String?,
                    val vk: String?,
                    val instagram: String?,
                    val status: String?,
                    val avatar: Avatar?,
                ) {
                    data class Avatar(
                        val filename: String?,
                        @Json(name = "base_64") val base64: String?,
                    )
                }
            }

            object Response {
                data class Profile(
                    @Json(name = "profile_data") val profileData: ProfileData?,
                ) {
                    data class ProfileData(
                        val name: String?,
                        val username: String?,
                        val birthday: String?,
                        val city: String?,
                        val vk: String?,
                        val instagram: String?,
                        val status: String?,
                        val avatar: String?,
                        val id: Int?,
                        val last: String?,
                        val online: Boolean?,
                        val created: String?,
                        val phone: String?,
                        @Json(name = "completed_task") val completedTask: Int?,
                        val avatars: Avatar?,
                    )

                    data class Avatar(
                        val avatar: String?,
                        val bigAvatar: String?,
                        val miniAvatar: String?,
                    )
                }
            }
        }

    }

    interface LocalSource {
        fun getMe(): Flow<String?>
        suspend fun saveMe(json: String)
        suspend fun removeMe()
    }

}