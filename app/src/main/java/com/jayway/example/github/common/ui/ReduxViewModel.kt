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

    private val stateStream = StateStream<State> {
        actions.compose(stateMachine)
    }

    override fun bind(screen: Screen<Action, State>): Disposable {
        val screenDisposable = CompositeDisposable()
        screenDisposable += stateStream.stream.bind(screen.render)
        screenDisposable += screen.userActions.subscribe {
            actions.accept(it)
        }
        return screenDisposable
    }

    override fun onCleared() {
        Timber.d("$this onCleared()")
        stateStream.reset()
    }


    //    private val stateHelper = StateStreamHelper<State>()
    //
    //    val states: Observable<State>
    //        get() {
    //            synchronized(stateHelper) {
    //                if (stateHelper.stateStream == null) {
    //                    stateHelper.stateStream = actions.compose(stateMachine)
    //                        .replay(1)
    //                        .autoConnect(1) { viewModelDisposable += it }
    //
    //                }
    //                return stateHelper.stateStream!!
    //            }
    //        }
    //
    //    class StateStreamHelper<State> {
    //        var stateStream: Observable<State>? = null
    //            set(value) {
    //                synchronized(this) {
    //                    value
    //                }
    //            }
    //
    //        fun reset() {
    //            stateStream = null
    //        }
    //
    //    }


    /**
     * A value type wrapper that exposes a hot [Observable] that can be easily aligned with the lifecycle
     * of the [ViewModel]
     *
     * The provided [states] lambda define the "real" stream of states for this [ViewModel]. The state machine, as such.
     * This class applies caching of that state stream by way of [Observable.replay], such that views
     * can rebind to the [ViewModel] and still get the retained state stream.
     *
     * Once the [ViewModel.onCleared] is invoked, [reset] should be invoked in order to both
     *
     *      a) dispose of the upstream subscription to [states] (which could involve expensive resources)
     *      b) clear the cache items.
     *
     * The latter is done by re-creating the cached stream.
     */
    private class StateStream<State>(private val states: () -> Observable<State>) {
        /**
         * A [Disposable] that controls the subscription to the upstream source, which is the state
         * stream.
         */
        private val disposable = CompositeDisposable()

        private var _stream: Observable<State>? = null

        val stream: Observable<State>
            get() {
                synchronized(this) {
                    if (_stream == null) {
                        _stream = states().replay(1)
                            .autoConnect(1) { disposable += it }
                    }
                    return _stream!! // guaranteed to be not null because synchronized
                }
            }

        /**
         * When invoked, it will dispose the underlying subscription to the [states] and clear any
         * cached state.
         */
        fun reset() {
            disposable.clear() // dispose the upstream connection
            synchronized(this) {
                _stream = null
            }
        }

    }
}