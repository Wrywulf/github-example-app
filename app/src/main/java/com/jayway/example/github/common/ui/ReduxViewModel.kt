package com.jayway.example.github.common.ui

import androidx.lifecycle.ViewModel
import com.artemzin.rxui.kotlin.bind
import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.reduxStore
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class ReduxViewModel<Action, State, Arguments> :
    ViewModel(),
    BindableViewModel<Screen<Action, State>, Arguments> {

    override fun bind(screen: Screen<Action, State>): Disposable {
        val disposable = CompositeDisposable()
        disposable += screen.userActions
            .compose(stateMachine)
            .subscribeOn(scheduler)
            .bind(screen.render)
        return disposable
    }

    private val stateMachine : ObservableTransformer<Action, State> = ObservableTransformer { upstream ->
        upstream
            .doOnNext { Timber.d("UserAction input: $it") }
            .reduxStore(
                initialState,
                sideEffects,
                ::reducer
            )
            .distinctUntilChanged()
            .doOnNext { Timber.d("State output: $it") }
    }

    protected open val scheduler : Scheduler = Schedulers.trampoline()

    protected abstract val initialState : State

    protected abstract val sideEffects : List<SideEffect<State, Action>>

    protected abstract fun reducer(state: State, action: Action): State
}