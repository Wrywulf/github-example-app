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

/**
 * An abstraction over [ViewModel] that enables RxRedux behaviour.
 *
 * Configuration and subscription to streams is handled completely transparent to the subclass, which
 * basically just have to declare: [Action] and [State] types as well as implement the list of [SideEffect]s
 * and the state reducer.
 *
 */
abstract class ReduxViewModel<Action, State, Arguments> : ViewModel(),
                                                          BindableViewModel<Screen<Action, State>, Arguments> {

    /**
     * The initial state of the [ViewModel], after instantiation.
     */
    protected abstract val initialState: State

    /**
     * The list of side effects involved in the manipulation of state for this view model. An example
     * of a side effect could be fetching data from a external store.
     *
     * Side effects really model the asynchronous tasks fired off within the state machine upon receipt
     * of an [Action]. Side effects do not change [State] directly, but they may output new [Action]s
     * that will.
     *
     * Another term for side-effect is "Use case".
     */
    protected abstract val sideEffects: List<SideEffect<State, Action>>

    /**
     * The core of the state machine in the view model. It receives every [Action] input to the view
     * model as well as internal [Action]s fired from the side effects.
     *
     * Based on current state and a received action, it outputs (synchronously) a state. The output
     * state could be the same state as before, or a new state.
     */
    protected abstract fun reducer(state: State, action: Action): State

    private val actions = PublishRelay.create<Action>()

    private val stateMachine: ObservableTransformer<Action, State> =
        ObservableTransformer { upstream ->
            upstream.doOnNext {
                Timber.v("UserAction input: $it")
            }
                .reduxStore(
                    initialState = initialState, sideEffects = sideEffects, reducer = ::reducer
                )
                .distinctUntilChanged()
                .doOnNext {
                    Timber.v("State output: $it")
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