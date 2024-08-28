package com.example.yourapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.core.UseCase
import com.example.yourapp.R
import com.example.yourapp.core.MyProfile.Intents
import com.example.yourapp.core.MyProfile.Model
import com.example.yourapp.core.MyProfile.Navigation
import com.example.yourapp.ui.composable.textField.DATA_FORMAT
import com.example.yourapp.util.MviViewModel
import com.example.yourapp.util.Utils.getZodiac
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getProfileUseCase: UseCase.GetProfile,
    private val logOutUseCase: UseCase.LogOut,
) :
    MviViewModel<Model, Intents, Navigation>() {

    init {
        viewModelScope.launch {
            getProfileUseCase.getFlow().collect { profile ->
                if (profile == null) {
                    navigate(Navigation.ToAuthorization)
                } else {
                    val avatar = if (profile.bigAvatar == null) ""
                    else "https://plannerok.ru/${profile.bigAvatar}"

                    val zodiac = try {
                        val date = SimpleDateFormat(DATA_FORMAT, Locale.ENGLISH)
                            .parse(profile.birthday!!)
                        getZodiac(date!!)
                    } catch (e: Exception) {
                        R.string.empty_text
                    }

                    state(state().copy(
                        error = null,
                        avatar = avatar,
                        zodiac = zodiac,
                        birthday = profile.birthday ?: "",
                        phone = profile.phone,
                        name = profile.name ?: "",
                        city = profile.city ?: "",
                        status = profile.status ?: "",
                    ))
                }
            }
        }
    }

    override fun initModel() = Model()

    override fun initIntents() = object : Intents {
        override val iLogOut: () -> Unit = {
            logOut()
        }

        override val iEditProfile: () -> Unit = {
            navigate(Navigation.ToEditMyProfile)
        }
        override val iChats: () -> Unit = {
            navigate(Navigation.ToChats)
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            logOutUseCase.logOut()
        }
    }

}