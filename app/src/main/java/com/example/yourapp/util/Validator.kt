package com.example.yourapp.util

import com.example.yourapp.R


data class ValidateModel(
    val isError: Boolean = false,
    val supportingR: Int = R.string.error
)

val userNameValidate: (txt: String)-> ValidateModel =  { txt ->
    if (txt.isEmpty()) {
        ValidateModel(
            isError = true,
            supportingR = R.string.required_field
        )
    } else if (txt.length < 5) {
        ValidateModel(
            isError = true,
            supportingR = R.string.must_characters_5
        )
    }  else {
        ValidateModel(
            isError = false,
            supportingR = R.string.correct
        )
    }
}

val phoneValidate: (txt: String)-> ValidateModel =  { txt ->
    if (txt.isEmpty()) {
        ValidateModel(
            isError = true,
            supportingR = R.string.required_field
        )
    } else {
        ValidateModel(
            isError = false,
            supportingR = R.string.correct
        )
    }
}

val codeValidate: (txt: String)-> ValidateModel =  { txt ->
    if (txt.isEmpty()) {
        ValidateModel(
            isError = true,
            supportingR = R.string.required_field
        )
    } else if (txt.length != 6) {
        ValidateModel(
            isError = true,
            supportingR = R.string.must_characters_6
        )
    } else {
        ValidateModel(
            isError = false,
            supportingR = R.string.correct
        )
    }
}