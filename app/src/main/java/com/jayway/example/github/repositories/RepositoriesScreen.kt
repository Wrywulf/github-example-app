package com.jayway.example.github.repositories

import android.view.ViewGroup
import com.artemzin.rxui.RxUi
import com.jayway.example.github.common.ui.Screen
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import timber.log.Timber


class RepositoriesScreen(viewGroup: ViewGroup) : Screen<RepositoriesViewModel.Action, RepositoriesViewModel.State> {

    override val userActions: Observable<RepositoriesViewModel.Action>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val render: Function<Observable<RepositoriesViewModel.State>, Disposable> = RxUi.ui {
        Timber.d("Repositories state:")
        Timber.d("  $it")
    }

}
