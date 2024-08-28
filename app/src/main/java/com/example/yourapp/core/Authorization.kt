package com.example.yourapp.core

import com.example.yourapp.util.Model.Error

object Authorization {

    data class Model(
        val wait: Boolean = false,
        val error: Error? = null,
        val pages: Pages = Pages.SendAuthCode,

        val phone: String = "",
        val countryCode: String = "",
        val code: String = "",
    ) {
        sealed interface Pages {
            data object SendAuthCode : Pages
            data object CheckAuthCode : Pages
        }
    }

    sealed interface Navigation {
        data object ToMyProfile : Navigation
        data class ToRegistration(val phone: String) : Navigation
    }

    interface Intents {
        val iChangePhone: (String) -> Unit
        val iChangeCountryCode: (String) -> Unit
        val iChangeCode: (String) -> Unit

        val iToSendAuthCode: () -> Unit

        val iSendAuthCode: () -> Unit
        val iAuthorize: () -> Unit
    }

}

























