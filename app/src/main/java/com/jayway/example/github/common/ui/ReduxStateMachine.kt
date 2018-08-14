package com.jayway.example.github.common.ui

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.reduxStore
import io.reactivex.ObservableTransformer
import timber.log.Timber

/**
 * TODO should this be merged into a viewmodel (for "flattenes") or exist alone (for modularity)? Inheritance is a problem...
 */
abstract class ReduxStateMachine<Action, State> {

    val stateMachine : ObservableTransformer<Action, State> = ObservableTransformer { upstream ->
        upstream
            .doOnNext { Timber.d("UserAction input: $it") }
            .reduxStore(
                initialState,
                sideEffects,
                ::reducer
            )
            .distinctUntilChanged()
    }

    abstract val initialState : State

    abstract val sideEffects : List<SideEffect<State, Action>>

    abstract fun reducer(state: State, action: Action): State
}