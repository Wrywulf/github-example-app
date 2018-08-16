package com.jayway.example.github.common.ui

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

interface Screen<Action, State> {

    val userActions: Observable<Action>
    val render: Function<Observable<State>, Disposable>
}