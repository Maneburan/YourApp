package com.example.data.mapperImp

import com.example.data.core.Profile
import com.example.domain.core.Entity
import com.example.data.core.Profile.RemoteSource
import javax.inject.Inject

internal class ProfileMapper  @Inject constructor(): Profile.Mapper {

    override fun toProfileRequest(editProfile: Entity.EditProfile): RemoteSource.Model.Request
        .Profile = RemoteSource.Model.Request.Profile(
            name = editProfile.name,
            username = editProfile.username,
            birthday = editProfile.birthday,
            city = editProfile.city,
            vk = editProfile.vk,
            instagram = editProfile.instagram,
            status = editProfile.status,
            avatar = RemoteSource.Model.Request.Profile.Avatar(
                filename = editProfile.filename,
                base64 = editProfile.base64,
            ),
        )

    override fun toProfileEntity(profileData: RemoteSource.Model.Response.Profile.ProfileData): Entity
        .Profile = Entity.Profile(
            avatar = profileData.avatars?.avatar,
            bigAvatar = profileData.avatars?.bigAvatar,
            miniAvatar = profileData.avatars?.miniAvatar,
            phone = profileData.phone!!,
            name = profileData.name,
            username = profileData.username!!,
            birthday = profileData.birthday,
            city = profileData.city,
            vk = profileData.vk,
            instagram = profileData.instagram,
            status = profileData.status,
        )

}