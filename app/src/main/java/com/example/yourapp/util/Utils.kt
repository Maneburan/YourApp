package com.example.yourapp.util

import androidx.annotation.StringRes
import com.example.yourapp.R
import java.util.Calendar
import java.util.Date

object Utils {

    @StringRes
    fun getZodiac(date: Date): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)

        if ((day >= 22 && month == 11) || (day <= 20 && month == 0))
            return R.string.capricorn
        if ((day >= 21 && month == 0) || (day <= 19 && month == 1))
            return R.string.aquarius
        if ((day >= 20 && month == 1) || (day <= 20 && month == 2))
            return R.string.fish
        if ((day >= 21 && month == 2) || (day <= 20 && month == 3))
            return R.string.aries
        if ((day >= 21 && month == 3) || (day <= 21 && month == 4))
            return R.string.taurus
        if ((day >= 22 && month == 4) || (day <= 21 && month == 5))
            return R.string.twins
        if ((day >= 22 && month == 5) || (day <= 23 && month == 6))
            return R.string.cancer
        if ((day >= 24 && month == 6) || (day <= 23 && month == 7))
            return R.string.leo
        if ((day >= 24 && month == 7) || (day <= 23 && month == 8))
            return R.string.maid
        if ((day >= 24 && month == 8) || (day <= 23 && month == 9))
            return R.string.scales
        if ((day >= 24 && month == 9) || (day <= 22 && month == 10))
            return R.string.scorpio
        if ((day >= 23 && month == 10) || (day <= 21 && month == 11))
            return R.string.sagittarius
        return R.string.empty_text
    }

}