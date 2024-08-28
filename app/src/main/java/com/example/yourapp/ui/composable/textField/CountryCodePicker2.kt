package com.example.yourapp.ui.composable.textField

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.rejowan.ccpc.CCPUtils.Companion.getEmojiFlag
import com.rejowan.ccpc.Country
import com.rejowan.ccpc.CountryPickerBottomSheet
import com.rejowan.ccpc.CountryPickerDialog
import com.rejowan.ccpc.PickerCustomization
import com.rejowan.ccpc.ViewCustomization

@Composable
fun CountryCodePicker2(
    modifier: Modifier = Modifier,
    selectedCountry: Country = Country.Bangladesh,
    countryList: List<Country> = Country.getAllCountries(),
    onCountrySelected: (Country) -> Unit,
    viewCustomization: ViewCustomization = ViewCustomization(),
    pickerCustomization: PickerCustomization = PickerCustomization(),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textStyle: TextStyle = TextStyle(),
    showSheet: Boolean = false,
    itemPadding: Int = 10,
    clickable: Boolean
) {

    var country by remember { mutableStateOf(selectedCountry) }
    var isPickerOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        ) {
            if (clickable) isPickerOpen = true
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CountryView2(
            country = country,
            showFlag = viewCustomization.showFlag,
            showCountryIso = viewCustomization.showCountryIso,
            showCountryName = viewCustomization.showCountryName,
            showCountryCode = viewCustomization.showCountryCode,
            showArrow = viewCustomization.showArrow,
            textStyle = textStyle,
            itemPadding = itemPadding,
            clipToFull = viewCustomization.clipToFull
        )

        if (isPickerOpen) {
            if (showSheet) {
                CountryPickerBottomSheet(modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                    onDismissRequest = { isPickerOpen = false },
                    onItemClicked = {
                        onCountrySelected(it)
                        country = it
                        isPickerOpen = false

                    },
                    textStyle = textStyle,
                    listOfCountry = countryList,
                    pickerCustomization = pickerCustomization,
                    itemPadding = itemPadding,
                    backgroundColor = backgroundColor
                )
            } else {
                CountryPickerDialog(
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                    onDismissRequest = { isPickerOpen = false },
                    onItemClicked = {
                        onCountrySelected(it)
                        country = it
                        isPickerOpen = false

                    },
                    textStyle = textStyle,
                    listOfCountry = countryList,
                    pickerCustomization = pickerCustomization,
                    itemPadding = itemPadding,
                    backgroundColor = backgroundColor
                )
            }
        }

    }

}

@Composable
fun CountryView2(
    country: Country,
    textStyle: TextStyle,
    showFlag: Boolean,
    showCountryIso: Boolean,
    showCountryName: Boolean,
    showCountryCode: Boolean,
    showArrow: Boolean,
    itemPadding: Int = 10,
    clipToFull: Boolean = false
) {

    Row(
        modifier = Modifier.padding(itemPadding.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (showFlag) {
            Text(
                text = getEmojiFlag(country.countryIso),
                modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                style = textStyle
            )
        }

        if (showCountryName) {
            Text(
                text = country.countryName,
                modifier = Modifier.padding(end = 10.dp),
                style = textStyle
            )
        }

        if (showCountryIso) {
            Text(
                text = "(" + country.countryIso + ")",
                modifier = Modifier.padding(end = 20.dp),
                style = textStyle
            )
        }

        if (clipToFull) {
            Spacer(modifier = Modifier.weight(1f))
        }


        if (showCountryCode) {
            Text(
                text = country.countryCode,
                modifier = Modifier.padding(end = 5.dp),
                style = textStyle
            )
        }

        if (showArrow) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        }


    }


}