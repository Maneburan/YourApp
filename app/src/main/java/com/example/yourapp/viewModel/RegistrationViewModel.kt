package com.example.yourapp.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.Exceptions
import com.example.domain.core.UseCase
import com.example.yourapp.util.MviViewModel
import com.example.yourapp.util.Nav
import com.example.yourapp.core.Registration.Intents
import com.example.yourapp.core.Registration.Model
import com.example.yourapp.core.Registration.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val phoneRegistrationUseCase: UseCase.PhoneRegistration
) :
    MviViewModel<Model, Intents, Navigation>() {

    init {
        state(state().copy(phone = savedState[Nav.Args.PHONE]!!))
    }

    override fun initModel() = Model()

    override fun initIntents() = object : Intents {
        override val iChangeName: (String) -> Unit = { name ->
            changeName(name)
        }
        override val iChangeUserName: (String) -> Unit = { userName ->
            changeUserName(userName)
        }
        override val iRegister: () -> Unit = {
            register()
        }
        override val iCancel: () -> Unit = {
            navigate(Navigation.ToBack)
        }
    }

    private fun changeName(name: String) {
        state(state().copy(name = name))
    }

    private fun changeUserName(userName: String) {
        state(state().copy(userName = userName))
    }

    private fun register() {
        viewModelScope.launch {
            state(state().copy(wait = true, error = null))

            try {
                phoneRegistrationUseCase.register(phone = state().phone,
                    name = state().name, username = state().userName)
                navigate(Navigation.ToMyProfile)
            } catch (error: Exceptions.Incorrect) {
                state(state().copy(wait = false, error = "Ошибка: Ошибка: не корректные данные"))
            } catch (error: Exceptions.HttpException) {
                state(state().copy(wait = false, error = "Ошибка: ${error.message}"))
            }
        }
    }
}