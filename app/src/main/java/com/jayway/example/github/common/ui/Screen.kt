package com.jayway.example.github.common.ui

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

abstract class Screen<Action, State> {

    abstract val userActions: Observable<Action>
    abstract val render: Function<Observable<State>, Disposable>
}