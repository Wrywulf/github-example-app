package com.jayway.example.github.common.ui

import androidx.lifecycle.ViewModel
import com.artemzin.rxui.kotlin.bind
import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.reduxStore
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

abstract class ReduxViewModel<Action, State, Arguments> : ViewModel(),
                                                          BindableViewModel<Screen<Action, State>, Arguments> {

    protected abstract val initialState: State

    protected abstract val sideEffects: List<SideEffect<State, Action>>

    protected abstract fun reducer(state: State, action: Action): State

    private val actions = PublishRelay.create<Action>()

    private val stateMachine: ObservableTransformer<Action, State> =
        ObservableTransformer { upstream ->
            upstream.doOnNext {
                Timber.d("UserAction input: $it")
            }
                .reduxStore(
                    initialState = initialState, sideEffects = sideEffects, reducer = ::reducer
                )
                .distinctUntilChanged()
                .doOnNext {
                    Timber.d("State output: $it")
                }
        }


    /**
     * Observable instance cached throughout lifetime of [ViewModel].
     * Stream (subscription) is also scoped to the lifetime of the [ViewModel], once created.
     * This implies that after [Screen] is bound to the view model, this stream is created and will
     * remain in memory until [ViewModel.onCleared] (TODO: until ViewModel is GC'ed, really)
     */
    private val states: Observable<State> by lazy {
        actions.compose(stateMachine)
            .replay(1)
            .autoConnect()
    }

    override fun bind(screen: Screen<Action, State>): Disposable {
        val screenDisposable = CompositeDisposable()
        screenDisposable += states.bind(screen.render)
        screenDisposable += screen.userActions.subscribe {
            actions.accept(it)
        }
        return screenDisposable
    }

    override fun onCleared() {
        //TODO assuming this viewmodel instance is GC'ed shortly after this.. But if not, should we explicitly dispose the states stream?
        Timber.d("$this onCleared()")
    }
}