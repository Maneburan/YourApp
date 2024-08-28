package com.example.yourapp.core

import com.example.yourapp.util.Model.Error
import java.util.Date

object EditMyProfile {

    data class Model(
        val error: Error? = null,
        val wait: Boolean = false,
        val pages: Pages = Pages.Edit,

        val avatar: String = "",
        val name: String = "",
        val username: String = "",
        val birthday: String = "",
        val city: String = "",
        val vk: String = "",
        val instagram: String = "",
        val status: String = "",

        val filename: String? = null,
        val base64: String? = null,
    ) {
        sealed interface Pages {
            data object Edit : Pages
            data object TryAgain : Pages
        }
    }

    sealed interface Navigation {
        data object ToAuthorization : Navigation
        data object ToMyProfile : Navigation
        data object ToBack : Navigation
    }

    interface Intents {
        val iChangeName: (String) -> Unit
        val iChangeBirthday: (Date) -> Unit
        val iChangeCity: (String) -> Unit
        val iChangeVk: (String) -> Unit
        val iChangeInstagram: (String) -> Unit
        val iChangeStatus: (String) -> Unit
        val iChangeBase64: (String?) -> Unit
        val iChangeBirthDay: (String) -> Unit

        val iGetRemoteProfile: () -> Unit
        val iEditRemoteProfile: () -> Unit
        val iCancel: () -> Unit
    }
}