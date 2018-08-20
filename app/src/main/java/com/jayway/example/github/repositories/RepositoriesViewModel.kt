package com.jayway.example.github.repositories

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.jayway.example.github.data.dto.GithubRepository
import com.jayway.example.github.common.ui.ReduxViewModel
import com.jayway.example.github.data.service.GithubRepositories
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepositoriesViewModel(private val githubRepositories: GithubRepositories) :
    ReduxViewModel<RepositoriesViewModel.Action, RepositoriesViewModel.State, String>() {
    override fun arguments(args: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val initialState: State = State.NoData.Initial

    override val sideEffects: List<SideEffect<State, Action>> = listOf(
        ::loadNextPageSideEffect
    )

    private fun loadNextPageSideEffect(
        actions: Observable<Action>, state: StateAccessor<State>
    ): Observable<RepositoriesViewModel.Action> {
        return actions.ofType(Action.LoadNextPageAction::class.java)
            .switchMap {

                val s = state()
                val page = when (s) {
                    is State.NoData   -> {
                        1
                    }
                    is State.WithData -> {
                        s.page + 1
                    }
                }
                loadRepos(page)
            }
    }

    private fun loadRepos(page: Int): Observable<RepositoriesViewModel.Action> {
        return githubRepositories.getAllRepos(page)
            .doOnSubscribe {
                Timber.d("### onSubscribe()")
            }
            .doOnError {
                Timber.d("### onError(): $it")
            }
            .subscribeOn(Schedulers.io())
            .toObservable()
            .doOnNext {
                Timber.d("got repos: $it")
            }
            .map { Action.PageLoadedAction(it, page) as Action }
            .onErrorReturn {
                Action.ErrorLoadingPage(it)
            }
            .startWith(Action.PageLoadingAction(page))
    }

    override fun reducer(state: State, action: Action): State {
        Timber.v("reducer. state: $state, action: $action")

        return when (action) {
            is RepositoriesViewModel.Action.PageLoadedAction  -> {
                var repositories = action.items
                if (state is State.WithData) {
                    repositories += state.repositories
                }
                State.WithData.ShowContentState(repositories, action.page)
            }
            is RepositoriesViewModel.Action.ErrorLoadingPage  -> {
                val errorMessage = action.error.message ?: "Unknown"
                when (state) {
                    is State.NoData   -> State.NoData.ErrorLoadingPage(errorMessage)
                    is State.WithData -> State.WithData.ErrorLoadingPage(
                        errorMessage, state.repositories, state.page
                    )
                }
            }

            is RepositoriesViewModel.Action.PageLoadingAction -> when (state) {
                is State.NoData   -> State.NoData.ShowLoading
                is State.WithData -> State.WithData.ShowLoading(
                    state.repositories, state.page
                )
            }
            else                                              -> {
                state
            }
        }
    }


    sealed class Action {
        /**
         * Typically emitted when the user when scrolls to the bottom of the list.
         * Could be emitted when screen loads for the 1st time (no data)
         */
        object LoadNextPageAction : Action()

        /**
         * Internal. Emitted when an async task is fetching page data
         */
        data class PageLoadingAction(val page: Int) : Action()

        /**
         * Internal. Emitted when the async task fetching page data completes
         */
        data class PageLoadedAction(val items: List<GithubRepository>, val page: Int) : Action()

        /**
         * Internal. Emitted when the async task fetching page data errors
         */
        data class ErrorLoadingPage(val error: Throwable) : Action()
    }

    sealed class State {
        /**
         * States implementing this contain a list repositories
         */
        private interface ContainsItems {
            val repositories: List<GithubRepository>
            val page: Int
        }

        /**
         * All subclasses contain no repository data etc.
         */
        sealed class NoData : State() {
            /**
             * The initial state when no data exists
             */
            object Initial : NoData()

            /**
             * Indicates loading when no existing data
             */
            object ShowLoading : NoData()

            /**
             * Indicates error when no existing data
             */
            data class ErrorLoadingPage(val message: String) : NoData()
        }

        /**
         * All subclasses contain some repository data already
         */
        sealed class WithData : State(), ContainsItems {

            /**
             * Indicates error loading additional data
             */
            data class ErrorLoadingPage(
                val message: String,
                override val repositories: List<GithubRepository>,
                override val page: Int
            ) : WithData()

            /**
             * State when content is ready
             */
            data class ShowContentState(
                override val repositories: List<GithubRepository>, override val page: Int
            ) : WithData()

            /**
             * Indicates loading of more data
             */
            data class ShowLoading(
                override val repositories: List<GithubRepository>, override val page: Int
            ) : WithData()
        }
    }
}




