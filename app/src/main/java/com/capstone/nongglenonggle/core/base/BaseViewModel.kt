package com.capstone.nongglenonggle.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface UiState
interface UiEvent
interface UiEffect

abstract class BaseViewModel<Event: UiEvent, State: UiState, Effect: UiEffect>(
    initialState: State
): ViewModel() {
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val currentState: State get() = _uiState.value
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow() // 단일소비모델

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun updateState(newState: State) {
        _uiState.value = newState
    }

    protected fun postEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected open fun handleEvent(event: Event) {}

    init {
        //subScribeEvent()
        viewModelScope.launch {
            event.collect(::handleEvent)
        }
    }

    protected inline fun <reified T : Event> onEvent(
        crossinline block: (T) -> Unit
    ) {
        viewModelScope.launch {
            event.filterIsInstance<T>()
                .collect {block(it)}
        }
    }

    fun<T> select(
        selector: (State) -> T
    ): StateFlow<T> =
        uiState.map(selector)
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.Eagerly, selector(currentState))

}