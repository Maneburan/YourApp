package com.example.yourapp.core

object MyProfile {

    data class Model(
        val error: String? = null,

        val avatar: String = "",
        val phone: String = "",
        val name: String = "",
        val city: String = "",
        val status: String = "",
    )

    sealed interface Navigation {
        data object ToAuthorization : Navigation
        data object ToEditMyProfile : Navigation
    }

    interface Intents {
        val iLogOut: () -> Unit
        val iEditProfile: () -> Unit
    }
}