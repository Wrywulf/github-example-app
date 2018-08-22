package com.jayway.example.github.common.ui

import com.freeletics.rxredux.SideEffect
import com.jayway.android.test.RxImmediateSchedulerRule
import com.jayway.android.test.TimberRule
import com.jayway.android.test.testRules
import com.jayway.example.github.TestScreen
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.lifecycle.CachingMode


class ReduxViewModelTestSpec : Spek({
                                        testRules(
                                            TimberRule("ReduxViewModelTestSpec"),
                                            RxImmediateSchedulerRule
                                        )

                                        given("new Screen and new ViewModel") {
                                            val screen by memoized(mode = CachingMode.SCOPE) { TestScreen<DummyAction, DummyState>() }
                                            val viewModel by memoized(mode = CachingMode.SCOPE) { TestReduxViewModel() }

                                            val screenDisposable = CompositeDisposable()
                                            on("screen is bound to viewmodel") {
                                                screenDisposable += viewModel.bind(screen)

                                                it("screen receives initial state") {
                                                    assertThat(screen.latestState).isEqualTo(
                                                        viewModel.initialState
                                                    )
                                                }
                                            }

                                            on("screen emits toggle action") {
                                                screen.injectAction(DummyAction.ActionToggle)

                                                it("screen receives toggled state") {
                                                    assertThat(screen.latestState).isEqualTo(
                                                        DummyState.StateB
                                                    )
                                                }
                                            }

                                            on("screen is recreated, bound to viewmodel") {
                                                screenDisposable.clear()
                                                val newScreen =
                                                    TestScreen<DummyAction, DummyState>()
                                                screenDisposable += viewModel.bind(newScreen)

                                                it("screen receives retained viewmodel state") {
                                                    assertThat(newScreen.latestState).isEqualTo(
                                                        DummyState.StateB
                                                    )
                                                }
                                            }


                                            on("screen is destroyed, viewmodel is cleared (ready for GC). Then new screen is created and bound") {
                                                screenDisposable.clear() // emulating user navigating back
                                                viewModel.onCleared()  // emulating framework clearing (should really destroy, but just clearing here since that is actually a "worse-case" test

                                                val newScreen =
                                                    TestScreen<DummyAction, DummyState>()
                                                screenDisposable += viewModel.bind(newScreen) // emulating user navigating to screen

                                                it("viewmodel state is re-set to inital") {
                                                    assertThat(newScreen.latestState).isEqualTo(
                                                        viewModel.initialState
                                                    )
                                                }
                                            }
                                        }
                                    })


sealed class DummyAction {
    object ActionToggle : DummyAction()
}

sealed class DummyState {
    object StateA : DummyState()
    object StateB : DummyState()
}


class TestReduxViewModel : ReduxViewModel<DummyAction, DummyState, Any>() {

    override fun arguments(args: Any?) {}

    public override val initialState: DummyState = DummyState.StateA

    public override val sideEffects: List<SideEffect<DummyState, DummyAction>> = listOf()

    override fun reducer(state: DummyState, action: DummyAction): DummyState {
        return when (action) {
            DummyAction.ActionToggle -> {
                when (state) {
                    DummyState.StateA -> DummyState.StateB
                    DummyState.StateB -> DummyState.StateA
                }
            }
        }
    }

    /**
     * Make public for testing
     */
    public override fun onCleared() {
        super.onCleared()
    }
}
