package com.jayway.example.github.repositories

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.jayway.example.github.R
import com.jayway.example.github.common.ui.ScreenFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : ScreenFragment() {
    private val model by viewModel<RepositoriesViewModel>()

    override val layoutId = R.layout.fragment_repositories

    override fun bindScreen(screenDisposable: CompositeDisposable, view: View, savedInstanceState: Bundle?) {
            val viewImpl = RepositoriesScreen(view as ViewGroup)
            screenDisposable += model.bind(viewImpl)
    }
}