package com.jayway.example.github.repositories

import android.view.ViewGroup
import com.artemzin.rxui.RxUi
import com.jakewharton.rxrelay2.PublishRelay
import com.jayway.example.github.common.ui.Screen
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import timber.log.Timber


class RepositoriesScreen(viewGroup: ViewGroup) :
    Screen<RepositoriesViewModel.Action, RepositoriesViewModel.State> {

    private val _userActions = PublishRelay.create<RepositoriesViewModel.Action>()

    override val userActions: Observable<RepositoriesViewModel.Action> = _userActions

    override val render: Function<Observable<RepositoriesViewModel.State>, Disposable> =
        RxUi.ui { state ->
            Timber.d("Repositories state:")
            Timber.d("  $state")

            when (state) {

                RepositoriesViewModel.State.NoData.Initial -> _userActions.accept(
                    RepositoriesViewModel.Action.LoadNextPageAction
                )
                /* RepositoriesViewModel.State.NoData.ShowLoading           -> TODO()
                 is RepositoriesViewModel.State.NoData.ErrorLoadingPage   -> TODO()
                 is RepositoriesViewModel.State.WithData.ErrorLoadingPage -> TODO()
                 is RepositoriesViewModel.State.WithData.ShowContentState -> TODO()
                 is RepositoriesViewModel.State.WithData.ShowLoading      -> TODO()*/
            }
        }

}
