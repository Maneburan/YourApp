package com.example.yourapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.Exceptions
import com.example.domain.core.Entity
import com.example.domain.core.UseCase
import com.example.yourapp.util.MviViewModel
import com.example.yourapp.core.EditMyProfile.Intents
import com.example.yourapp.core.EditMyProfile.Model
import com.example.yourapp.core.EditMyProfile.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditMyProfileViewModel @Inject constructor(
    private val editProfileUseCase: UseCase.EditProfile,
    private val getProfileUseCase: UseCase.GetProfile
) :
    MviViewModel<Model, Intents, Navigation>() {

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
        viewModelScope.launch {
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
//                        birthday = profile.birthday,
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
                        error = "Ошибка: не корректные данные"))
            } catch (error: Exceptions.HttpException) {
                state(
                    state().copy(wait = false, pages = Model.Pages.TryAgain,
                        error = "Ошибка: ${error.message}"))
            }
        }
    }

    private fun editRemoteProfile() {
        viewModelScope.launch {
            state(state().copy(wait = true, error = null))

            try {
                editProfileUseCase.editMe(
                    Entity.EditProfile(
                        name = state().name.ifEmpty { null },
                        username = state().username,
                        birthday = null,//getState().birthday,
                        city = state().city.ifEmpty { null },
                        vk = state().vk.ifEmpty { null },
                        instagram = state().instagram.ifEmpty { null },
                        status = state().status.ifEmpty { null },
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
                        error = "Ошибка: Ошибка: не корректные данные"))
            } catch (error: Exceptions.HttpException) {
                state(
                    state().copy(wait = false, pages = Model.Pages.Edit,
                        error = "Ошибка: ${error.message}"))
            }
        }
    }

}