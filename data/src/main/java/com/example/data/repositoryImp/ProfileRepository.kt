package com.example.data.repositoryImp

import com.example.data.Utils
import com.example.data.core.Profile.LocalSource
import com.example.data.core.Profile.Mapper
import com.example.data.core.Profile.RemoteSource
import com.example.domain.core.Entity
import com.example.domain.Exceptions
import com.example.domain.core.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

internal class ProfileRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val mapper: Mapper,
)
    : Repository.Profile {

    override suspend fun editMe(accessToken: String, editProfileEntity: Entity.EditProfile) {
        try {
            remoteSource.editMe("Bearer $accessToken",
                mapper.toProfileRequest(editProfileEntity))
        } catch (httpException: HttpException) {
            if (httpException.code() == 401) {
                throw Exceptions.Unauthorized()
            } else {
                throw Exceptions.HttpException(httpException.message(), httpException.code())
            }
        }
    }

    override suspend fun getMe(accessToken: String): Entity.Profile {
        try {
            val profileResponse = remoteSource.getMe("Bearer $accessToken")
            val model = profileResponse.profileData
            if (model?.phone == null || model.username == null) throw Exceptions.Incorrect()

            val jsonString = Utils.moshiToString(model)
            localSource.saveMe(jsonString)

            return mapper.toProfileEntity(model)
        } catch (httpException: HttpException) {
            if (httpException.code() == 401) {
                throw Exceptions.Unauthorized()
            } else {
                throw Exceptions.HttpException(httpException.message(), httpException.code())
            }
        }
    }

    override fun getMeFlow(): Flow<Entity.Profile?> = flow {
        localSource.getMe().collect { str ->
            var profileEntity: Entity.Profile? = null

            if (str != null) {
                val model = Utils.stringToMoshi<RemoteSource.Model.Response.Profile.ProfileData>(str)
                profileEntity = if (model?.phone == null || model.username == null) null
                else mapper.toProfileEntity(model)
            }

            emit(profileEntity)
        }
    }

    override suspend fun removeMe() {
        localSource.removeMe()
    }
}