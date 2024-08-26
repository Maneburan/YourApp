package com.example.domain.core

object Entity {

    data class AuthToken(
        val access: String,
        val refresh: String,
    )

    data class CheckAuthCode(
        val authToken: AuthToken?,
        val isUserExists: Boolean,
    )

    data class EditProfile(
        val name: String?,
        val username: String,
        val birthday: String?,
        val city: String?,
        val vk: String?,
        val instagram: String?,
        val status: String?,
        val filename: String?,
        val base64: String?,
    )

    data class Profile(
        val avatar: String?,
        val bigAvatar: String?,
        val miniAvatar: String?,

        val phone: String,
        val name: String?,
        val username: String,
        val birthday: String?,
        val city: String?,
        val vk: String?,
        val instagram: String?,
        val status: String?,
    )

}