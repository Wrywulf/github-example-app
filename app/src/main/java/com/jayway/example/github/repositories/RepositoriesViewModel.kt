package com.jayway.example.github.repositories

import com.freeletics.rxredux.SideEffect
import com.jayway.example.github.data.dto.GithubRepository
import com.jayway.example.github.common.ui.ReduxViewModel

class RepositoriesViewModel : ReduxViewModel<RepositoriesViewModel.Action, RepositoriesViewModel.State, String>() {
    override fun arguments(args: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val initialState: State = State.LoadingInitial

    override val sideEffects: List<SideEffect<State, Action>> = emptyList()

    override fun reducer(state: State, action: Action): State {
        return when (action) {
            RepositoriesViewModel.Action.LoadInitialPageAction      -> TODO()
            RepositoriesViewModel.Action.LoadNextPageUserAction     -> TODO()
            is RepositoriesViewModel.Action.PageLoadedAction        -> TODO()
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
        object LoadingInitial : State()
        data class ShowContentState(val repositories: List<GithubRepository>, val page: Int) : State()
        data class ShowContentAndLoadNextPageState(val repositories: List<GithubRepository>, val page: Int) :
            State()
    }
}




