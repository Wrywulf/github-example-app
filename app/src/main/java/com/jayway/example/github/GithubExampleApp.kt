package com.jayway.example.github

import android.app.Application
import com.jayway.example.github.common.logging.Snack
import com.jayway.example.github.di.baseModule
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class GithubExampleApp : Application() {


    val disposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(baseModule))

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Snack.install(Snack.RealSnackBarWrapper())
            installStetho(this)
        }

    }
}