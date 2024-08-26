package com.example.data.mapperImp

import com.example.data.core.AuthToken
import com.example.domain.core.Entity
import com.example.data.core.AuthToken.RemoteSource
import com.example.data.core.AuthToken.LocalSource
import javax.inject.Inject

internal class AuthTokenMapper  @Inject constructor(): AuthToken.Mapper {

    override fun toRefreshTokenRequest(refreshToken: String): RemoteSource.Model.Request
        .RefreshToken = RemoteSource.Model.Request.RefreshToken(
            refreshToken = refreshToken
        )

    override fun toAuthTokenLocal(authTokenEntity: Entity.AuthToken): LocalSource.Model
        .AuthToken = LocalSource.Model.AuthToken(
            access = authTokenEntity.access,
            refresh = authTokenEntity.refresh,
        )

    override fun toAuthTokenEntity(access: String, refresh: String): Entity.AuthToken = Entity
        .AuthToken(
            access = access,
            refresh = refresh,
        )

}