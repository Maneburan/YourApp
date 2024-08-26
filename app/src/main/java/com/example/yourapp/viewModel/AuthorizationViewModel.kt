package com.example.yourapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.Exceptions
import com.example.domain.core.UseCase
import com.example.yourapp.util.MviViewModel
import com.example.yourapp.core.Authorization.Intents
import com.example.yourapp.core.Authorization.Model
import com.example.yourapp.core.Authorization.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val phoneAuthUseCase: UseCase.PhoneAuth
):
    MviViewModel<Model, Intents, Navigation>() {

    private val lastException = CoroutineExceptionHandler { _, e ->
//            setState(getState().copy(wait = false, error = "Ошибка: ${e.message}",
//                pages = Model.Pages.SendAuthCode, code = ""))
        throw e
    }

    override fun initModel() = Model()

    override fun initIntents() = object : Intents {
        override val iChangePhone: (String) -> Unit = { phone ->
            state(state().copy(phone = phone))
        }
        override val iSendAuthCode: () -> Unit = {
            sendAuthCode()
        }
        override val iChangeCode: (String) -> Unit = { code ->
            state(state().copy(code = code))
        }
        override val iToSendAuthCode: () -> Unit = {
            state(state().copy(pages = Model.Pages.SendAuthCode))
        }
        override val iAuthorize: () -> Unit = {
            authorize()
        }
    }

    private fun authorize() {
        viewModelScope.launch(lastException) {
            state(state().copy(wait = true, error = null))

            try {
                val profile = phoneAuthUseCase.authorize(phone = state().phone,
                    code = state().code)
                if (profile != null) {
                    navigate(Navigation.ToMyProfile)
                } else {
                    state(state().copy(wait = false, error = null,
                        pages = Model.Pages.SendAuthCode))
                    navigate(Navigation.ToRegistration(phone = state().phone))
                }
            } catch (error: Exceptions.Incorrect) {
                state(state().copy(wait = false, error = "Ошибка: не корректные данные"))
            } catch (error: Exceptions.HttpException) {
                state(state().copy(wait = false, error = "Ошибка: ${error.message}"))
            }
        }
    }

    private fun sendAuthCode() {
        viewModelScope.launch(lastException) {
            state(state().copy(wait = true, error = null))

            try {
                val isSuccess = phoneAuthUseCase.sendAuthCode(phone = state().phone)

                if (isSuccess) {
                    state(
                        state().copy(wait = false,
                            pages = Model.Pages.CheckAuthCode
                        ))
                } else {
                    state(
                        state().copy(wait = false,
                            error = "Ошибка: Код не отправлен (повторите попытку)")
                    )
                }
            } catch (error: Exceptions.Incorrect) {
                state(state().copy(wait = false, error = "Ошибка: Ошибка: не корректные данные"))
            } catch (error: Exceptions.HttpException) {
                state(state().copy(wait = false, error = "Ошибка: ${error.message}"))
            }
        }
    }
}