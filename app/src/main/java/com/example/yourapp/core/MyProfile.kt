package com.example.yourapp.core

import androidx.annotation.StringRes
import com.example.yourapp.R
import com.example.yourapp.util.Model.Error

object MyProfile {

    data class Model(
        val error: Error? = null,

        val avatar: String = "",
        val phone: String = "",
        val name: String = "",
        val city: String = "",
        val birthday: String = "",
        @StringRes val zodiac: Int = R.string.empty_text,
        val status: String = "",
    )

    sealed interface Navigation {
        data object ToAuthorization : Navigation
        data object ToEditMyProfile : Navigation
        data object ToChats : Navigation
    }

    interface Intents {
        val iLogOut: () -> Unit
        val iEditProfile: () -> Unit
        val iChats: () -> Unit
    }
}