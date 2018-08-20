package com.jayway.example.github

import com.artemzin.rxui.test.TestRxUi
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.jayway.example.github.common.ui.Screen
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import timber.log.Timber

/**
 * A simple [Screen] for which input actions [A] can be injected and output state [S] can be observed
 */
open class TestScreen<A, S> : Screen<A, S> {

    private val internalRecordedStates = mutableListOf<S>()
    private val actionStream = PublishRelay.create<A>()
    val recordedStates: List<S> = internalRecordedStates
    val latestState: S
        get() = recordedStates.last()
    val stateStream: BehaviorRelay<S> = BehaviorRelay.create()

    init {
        stateStream.subscribe {state ->
            internalRecordedStates.add(state)
            Timber.v("Recorded states:")
            internalRecordedStates.forEachIndexed { index, s ->
                Timber.v("  [$index]: $s")
            }
        }
    }

    fun injectAction(action: A) = actionStream.accept(action)

    override val userActions: Observable<A> = actionStream

    override val render: Function<Observable<S>, Disposable> = TestRxUi.testUi {
        stateStream.accept(it)
    }

}