package com.example.yourapp.core

import com.example.yourapp.util.Model.Error

object Registration {

    data class Model(
        val wait: Boolean = false,
        val error: Error? = null,

        val phone: String = "",
        val name: String = "",
        val userName: String = "",
    )

    sealed interface Navigation {
        data object ToMyProfile : Navigation
        data object ToBack : Navigation
    }

    interface Intents {
        val iChangeName: (String) -> Unit
        val iChangeUserName: (String) -> Unit

        val iRegister: () -> Unit
        val iCancel: () -> Unit
    }
}