package org.itis.project.sharedlogic.core.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State : Any, Intent : Any, Effect : Any>(
    initialState: State
) : CommonViewModel()  {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    protected fun updateState(block: (State) -> State) {
        _state.update(block)
    }

    protected fun emitEffect(effect: Effect) {
        _effect.tryEmit(effect)
    }

    protected fun currentState(): State = _state.value

    abstract fun obtainIntent(intent: Intent)
}