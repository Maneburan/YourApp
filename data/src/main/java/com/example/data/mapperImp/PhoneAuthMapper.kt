package com.example.data.mapperImp

import com.example.data.core.PhoneAuth
import com.example.domain.core.Entity
import com.example.data.core.PhoneAuth.RemoteSource
import javax.inject.Inject

internal class PhoneAuthMapper  @Inject constructor(): PhoneAuth.Mapper {

    override fun toSendAuthCodeRequest(phone: String): RemoteSource.Model.Request
        .SendAuthCode = RemoteSource.Model.Request.SendAuthCode(
            phone = phone
        )

    override fun toCheckAuthCodeRequest(
        phone: String,
        code: String
    ): RemoteSource.Model.Request.CheckAuthCode = RemoteSource.Model.Request.CheckAuthCode(
        phone = phone,
        code = code
    )

    override fun toAuthTokenEntity(access: String, refresh: String): Entity.AuthToken =
        Entity.AuthToken(
            access = access,
            refresh = refresh,
        )

    override fun toCheckAuthCodeEntity(checkAuthCodeResponse: RemoteSource.Model.Response
        .CheckAuthCode): Entity.CheckAuthCode {
        val access = checkAuthCodeResponse.accessToken
        val refresh = checkAuthCodeResponse.refreshToken

        return Entity.CheckAuthCode(
            authToken = if (access == null || refresh == null) null
            else Entity.AuthToken(access = access, refresh = refresh),
            isUserExists = checkAuthCodeResponse.isUserExists!!
        )
    }

    override fun toRegisterRequest(
        phone: String,
        name: String,
        username: String
    ): RemoteSource.Model.Request.Register = RemoteSource.Model.Request.Register(
        phone = phone,
        name = name,
        username = username
    )

}