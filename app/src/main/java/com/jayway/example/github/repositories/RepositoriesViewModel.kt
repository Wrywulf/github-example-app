package com.jayway.example.github.repositories

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.jayway.example.github.data.dto.GithubRepository
import com.jayway.example.github.common.ui.ReduxViewModel
import com.jayway.example.github.data.service.GithubRepositories
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepositoriesViewModel(private val githubRepositories: GithubRepositories) : ReduxViewModel<RepositoriesViewModel.Action, RepositoriesViewModel.State, String>() {
    override fun arguments(args: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val initialState: State = State.Initial

    override val sideEffects: List<SideEffect<State, Action>> = listOf(
        ::loadFirstPageSideEffect
    )

    private fun loadFirstPageSideEffect(actions: Observable<Action>, state: StateAccessor<State>): Observable<out RepositoriesViewModel.Action> {

        return actions.ofType(Action.LoadInitialPageAction::class.java)
            .filter { state() == State.ShowLoading }
            .switchMap {
                githubRepositories.getAllRepos(1).subscribeOn(Schedulers.io()).toObservable()
            }
            .doOnNext { Timber.d("got repos: $it") }
            .map { Action.PageLoadedAction(it, 1) }
    }


    override fun reducer(state: State, action: Action): State {
        return when (action) {
            RepositoriesViewModel.Action.LoadInitialPageAction      ->
                when (state) {
                    State.Initial -> State.ShowLoading
                    else          -> state
                }
            RepositoriesViewModel.Action.LoadNextPageUserAction     -> TODO()
            is RepositoriesViewModel.Action.PageLoadedAction        ->
                State.ShowContentState(action.items, action.page)
            RepositoriesViewModel.Action.StartLoadingNextPageAction -> TODO()
        }
    }

    sealed class Action {
        object LoadInitialPageAction : Action()
        object LoadNextPageUserAction : Action()
        data class PageLoadedAction(val items: List<GithubRepository>, val page: Int) : Action()
        object StartLoadingNextPageAction : Action()
    }

    sealed class State {
        object Initial : State()
        object ShowLoading : State()
        data class ShowContentState(val repositories: List<GithubRepository>, val page: Int) : State()
        data class ShowContentAndLoadNextPageState(val repositories: List<GithubRepository>, val page: Int) :
            State()
    }
}




