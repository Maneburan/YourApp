package com.example.yourapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.Exceptions
import com.example.domain.core.Entity
import com.example.domain.core.UseCase
import com.example.yourapp.R
import com.example.yourapp.core.EditMyProfile.Intents
import com.example.yourapp.core.EditMyProfile.Model
import com.example.yourapp.core.EditMyProfile.Navigation
import com.example.yourapp.ui.composable.textField.DATA_FORMAT
import com.example.yourapp.util.Model.Error
import com.example.yourapp.util.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditMyProfileViewModel @Inject constructor(
    private val editProfileUseCase: UseCase.EditProfile,
    private val getProfileUseCase: UseCase.GetProfile
) :
    MviViewModel<Model, Intents, Navigation>() {

    private val lastException = CoroutineExceptionHandler { _, e ->
        state(state().copy(wait = false, error = Error("${e.message}", null),
            pages = Model.Pages.TryAgain))
    }

    init {
        intents.iGetRemoteProfile()
    }

    override fun initModel() = Model()

    override fun initIntents() = object : Intents {
        override val iChangeName: (String) -> Unit = { name ->
            state(state().copy(name = name))
        }
        override val iChangeBirthday: (Date) -> Unit = { birthday ->
//                setState(getState().copy(birthday = birthday))
        }
        override val iChangeCity: (String) -> Unit = { city ->
            state(state().copy(city = city))
        }
        override val iChangeVk: (String) -> Unit = { vk ->
            state(state().copy(vk = vk))
        }
        override val iChangeInstagram: (String) -> Unit = { instagram ->
            state(state().copy(instagram = instagram))
        }
        override val iChangeStatus: (String) -> Unit = { status ->
            state(state().copy(status = status))
        }
        override val iChangeBase64: (String?) -> Unit = { base64 ->
            val filename = if (base64 == null) null else "filename"
            state(state().copy(base64 = base64, filename = filename))
        }
        override val iChangeBirthDay: (String) -> Unit = { birthday ->
            state(state().copy(birthday = birthday))
        }

        override val iGetRemoteProfile: () -> Unit = {
            getRemoteProfile()
        }

        override val iEditRemoteProfile: () -> Unit = {
            editRemoteProfile()
        }
        override val iCancel: () -> Unit = {
            navigate(Navigation.ToBack)
        }
    }

    private fun getRemoteProfile() {
        viewModelScope.launch(lastException) {
            state(state().copy(wait = true, error = null))

            try {
                val profile = getProfileUseCase.get()
                val avatar = if (profile.bigAvatar == null) ""
                else "https://plannerok.ru/${profile.bigAvatar}"

                state(
                    state().copy(
                        error = null,
                        wait = false,
                        pages = Model.Pages.Edit,

                        name = profile.name ?: "",
                        username = profile.username,
                        avatar = avatar,
                        birthday = profile.birthday ?: "",
                        city = profile.city ?: "",
                        vk = profile.vk ?: "",
                        instagram = profile.instagram ?: "",
                        status = profile.status ?: "",
                    ))
            } catch (error: Exceptions.Unauthorized) {
                navigate(Navigation.ToAuthorization)
            } catch (error: Exceptions.Incorrect) {
                state(
                    state().copy(wait = false, pages = Model.Pages.TryAgain,
                        error = Error(null, R.string.incorrect_data)))
            } catch (error: Exceptions.HttpException) {
                state(
                    state().copy(wait = false, pages = Model.Pages.TryAgain,
                        error = Error(error.message, null)))
            }
        }
    }

    private fun editRemoteProfile() {
        viewModelScope.launch(lastException) {
            state(state().copy(wait = true, error = null))

            try {
                val birthDay = try {
                    SimpleDateFormat(DATA_FORMAT, Locale.ENGLISH)
                        .parse(state().birthday)?.time
                } catch (e: Exception) { null }

                editProfileUseCase.editMe(
                    Entity.EditProfile(
                        name = state().name,
                        username = state().username,
                        birthday = birthDay,
                        city = state().city,
                        vk = state().vk,
                        instagram = state().instagram,
                        status = state().status,
                        filename = state().filename,
                        base64 = state().base64,
                    )
                )

                navigate(Navigation.ToMyProfile)
            } catch (error: Exceptions.Unauthorized) {
                navigate(Navigation.ToAuthorization)
            } catch (error: Exceptions.Incorrect) {
                state(
                    state().copy(wait = false, pages = Model.Pages.Edit,
                        error = Error(null, R.string.incorrect_data)))
            } catch (error: Exceptions.HttpException) {
                state(
                    state().copy(wait = false, pages = Model.Pages.Edit,
                        error = Error(error.message, null)))
            }
        }
    }

}