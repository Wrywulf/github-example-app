package com.jayway.example.github.common.ui

import io.reactivex.disposables.Disposable

interface BindableViewModel<in S : Screen<*, *>, in Data> {
    fun arguments(args: Data? = null)

    fun bind(screen: S): Disposable
}