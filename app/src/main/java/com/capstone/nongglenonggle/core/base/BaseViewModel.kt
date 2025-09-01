package com.capstone.nongglenonggle.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

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

    init {
        subScribeEvent()
    }

    // ViewModel 생명주기 동안 event를 구독하여 상태를 업데이트한다.
    // collect된 이벤트는 handleEvent()로 처리된다.
    private fun subScribeEvent() {
        viewModelScope.launch {
            event.collect{
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)
}