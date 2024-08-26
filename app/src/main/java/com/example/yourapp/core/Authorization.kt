package com.example.yourapp.core

object Authorization {

    data class Model(
        val wait: Boolean = false,
        val error: String? = null,
        val pages: Pages = Pages.SendAuthCode,

        val phone: String = "",
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
        val iChangeCode: (String) -> Unit

        val iToSendAuthCode: () -> Unit

        val iSendAuthCode: () -> Unit
        val iAuthorize: () -> Unit
    }

}

























