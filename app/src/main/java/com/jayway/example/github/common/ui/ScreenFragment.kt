package com.jayway.example.github.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class ScreenFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()

    @get:LayoutRes
    abstract val layoutId :  Int

    /**
     * Called from within [onViewCreated]
     * @param compositeDisposable the composite disposable to use for the screen
     */
    abstract fun bindScreen(screenDisposable: CompositeDisposable, view: View, savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindScreen(compositeDisposable, view, savedInstanceState)
    }


    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

}