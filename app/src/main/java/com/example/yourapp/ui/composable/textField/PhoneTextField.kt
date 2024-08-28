package com.example.yourapp.ui.composable.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import com.example.yourapp.R
import com.example.yourapp.ui.composable.ClearIcon
import com.rejowan.ccpc.CCPTransformer
import com.rejowan.ccpc.CCPUtils
import com.rejowan.ccpc.CCPValidator
import com.rejowan.ccpc.Country

@Composable
fun PhoneTextField(
    number: String,
    enabled: Boolean,
    onValueChange: (countryCode: String, number: String, isValid: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val inspectionMode = LocalInspectionMode.current
    var country by remember { mutableStateOf(Country.Bangladesh) }

    val autoPick = remember { mutableStateOf(true) }

    if (autoPick.value) {
        if (!inspectionMode) {
            CCPUtils.getCountryAutomatically(context = context).let {
                it?.let {
                    country = it
                }
            }
        }
    }

    val validatePhoneNumber = remember(context) { CCPValidator(context = context) }

    var isNumberValid: Boolean by rememberSaveable(country, number) {
        mutableStateOf(
            validatePhoneNumber(
                number = number, countryCode = country.countryCode
            ) && number.isNotEmpty(),
        )
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = number,
        onValueChange = {
            isNumberValid = validatePhoneNumber(
                number = it, countryCode = country.countryCode
            )
            onValueChange(country.countryCode, it, isNumberValid)
        },
        singleLine = true,
        shape = OutlinedTextFieldDefaults.shape,
        isError = !isNumberValid,
        visualTransformation = CCPTransformer(context, country.countryIso),
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        trailingIcon = {
            ClearIcon(
                text = number,
                enabled = enabled,
                onText = {
                    onValueChange(country.countryCode, it, false)
                }
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.enter_phone),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        leadingIcon = {
            CountryCodePicker2(
                selectedCountry = country,
                countryList = Country.getAllCountries(),
                onCountrySelected = {
                    country = it
                    isNumberValid = validatePhoneNumber(
                        number = number, countryCode = it.countryCode
                    )
                    autoPick.value = false
                    onValueChange(country.countryCode, number, isNumberValid)
                },
                clickable = enabled
            )
        },
    )

//    CountryCodePickerTextField(number = , onValueChange = )
}