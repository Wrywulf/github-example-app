package com.jayway.example.github.repositories

import com.artemzin.rxui.test.TestRxUi
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.jayway.android.test.ImmediateSchedulersRule
import com.jayway.android.test.TimberRule
import com.jayway.example.github.common.ui.Screen
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import timber.log.Timber


class RepositoriesViewModelTest {

    // REMOVE COMMENT TO ENABLE LOGGING
    companion object {
        @JvmField
        @ClassRule
        val TIMBER_RULE = TimberRule("RepositoriesViewModelTest")
    }

    @Rule
    @JvmField
    val rxRule: ImmediateSchedulersRule = ImmediateSchedulersRule()

    lateinit var viewModel: RepositoriesViewModel

    @Before
    fun setup() {
        viewModel = RepositoriesViewModel()
    }

    @Test
    fun `initial state is loading first page`() {
        Timber.d("Starting test")
        val screen = TestScreen<RepositoriesViewModel.Action, RepositoriesViewModel.State>()
        viewModel.bind(screen)

        Assertions.assertThat(screen.latestState)
            .isEqualTo(RepositoriesViewModel.State.LoadingInitial)
    }
}

class TestScreen<A, S> : Screen<A, S>() {

    private val internalRecordedStates = mutableListOf<S>()

    private val actionStream = PublishRelay.create<A>()

    val recordedStates: List<S> = internalRecordedStates

    val latestState: S
        get() = recordedStates.last()

    val stateStream: BehaviorRelay<S> = BehaviorRelay.create()

    init {
        stateStream.subscribe {
            internalRecordedStates.add(it)
        }
    }

    fun injectAction(action: A) = actionStream.accept(action)

    override val userActions: Observable<A> = Observable.empty()

    override val render: Function<Observable<S>, Disposable> = TestRxUi.testUi {
        Timber.d("TestScreen state: $it")
        stateStream.accept(it)
    }
}
