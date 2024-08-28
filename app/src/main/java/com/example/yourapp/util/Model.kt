package com.example.yourapp.util

import androidx.annotation.StringRes

object Model {

    data class Error(
        val msg: String?,
        @StringRes val res: Int?
    )
}