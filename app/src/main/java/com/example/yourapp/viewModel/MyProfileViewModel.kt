package com.example.yourapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.core.UseCase
import com.example.yourapp.util.MviViewModel
import com.example.yourapp.core.MyProfile.Intents
import com.example.yourapp.core.MyProfile.Model
import com.example.yourapp.core.MyProfile.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getProfileUseCase: UseCase.GetProfile,
    private val logOutUseCase: UseCase.LogOut,
) :
    MviViewModel<Model, Intents, Navigation>() {

    private val lastException = CoroutineExceptionHandler { _, e ->
        val error = "Error (${e.message ?: "unknown"})"
        state(state().copy(error = error))
    }

    init {
        viewModelScope.launch {
            getProfileUseCase.getFlow().collect { profile ->
                if (profile == null) {
                    navigate(Navigation.ToAuthorization)
                } else {
                    val avatar = if (profile.bigAvatar == null) ""
                    else "https://plannerok.ru/${profile.bigAvatar}"

                    state(state().copy(
                        error = null,
                        avatar = avatar,
                        phone = profile.phone ?: "",
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
    }

    private fun logOut() {
        viewModelScope.launch(lastException) {
            logOutUseCase.logOut()
        }
    }

}