package com.example.data.repositoryImp

import com.example.data.core.AuthToken.LocalSource
import com.example.data.core.AuthToken.Mapper
import com.example.data.core.AuthToken.RemoteSource
import com.example.domain.Exceptions
import com.example.domain.core.Entity
import com.example.domain.core.Repository
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import javax.inject.Inject

internal class AuthTokenRepository @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
    private val mapper: Mapper,
)
    : Repository.AuthToken {

    override suspend fun refresh(authTokenEntity: Entity.AuthToken): Entity.AuthToken {
        try {
            val model = remoteSource.refreshToken("Bearer ${authTokenEntity.refresh}",
                mapper.toRefreshTokenRequest(refreshToken = authTokenEntity.refresh))

            if (model.accessToken == null || model.refreshToken == null) throw Exceptions.Incorrect()

            val result = mapper.toAuthTokenEntity(access = model.accessToken, refresh = model.refreshToken)
            save(result)
            return result
        } catch (httpException: HttpException) {
            if (httpException.code() == 404 || httpException.code() == 422)
                throw Exceptions.Unauthorized()
            throw Exceptions.HttpException(httpException.message(), httpException.code())
        }
    }

    override suspend fun get(): Entity.AuthToken? {
        val model = localSource.get().firstOrNull()
        return if (model != null) Entity.AuthToken(access = model.access, refresh = model.refresh)
        else null
    }

    override suspend fun remove() {
        localSource.remove()
    }

    override suspend fun save(authTokenEntity: Entity.AuthToken) {
        localSource.save(mapper.toAuthTokenLocal(authTokenEntity))
    }
}