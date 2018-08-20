package com.jayway.example.github

import android.app.Application
import com.jayway.example.github.common.logging.Snack
import com.jayway.example.github.di.baseModule
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class GithubExampleApp : Application() {


    val disposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(baseModule))

        @Suppress("ConstantConditionIf") if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Snack.install(Snack.RealSnackBarWrapper())
            installStetho(this)
        }

    }
}