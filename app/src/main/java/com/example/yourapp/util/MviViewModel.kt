package com.example.yourapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<Model, Intents, Navigation>: ViewModel() {

    //external fields
    val intents by lazy { initIntents() }
    val navigationFlow by lazy { navigationChannel.receiveAsFlow() }

    //internal fields
    private val model by lazy { initModel() }
    private val _stateFlow = MutableStateFlow(model)

    private val navigationChannel: Channel<Navigation> = Channel(Channel.CONFLATED)

    //external behavior
    @Composable
    fun observeState(): State<Model> {
        return _stateFlow.collectAsState()
    }

    //internal behavior
    protected abstract fun initModel(): Model
    protected abstract fun initIntents(): Intents

    protected fun navigate(navigation: Navigation) {
        viewModelScope.launch {
            navigationChannel.send(navigation)
        }
    }

    protected fun state(): Model {
        return _stateFlow.value
    }

    protected fun state(model: Model) {
        _stateFlow.value = model
    }

}






















