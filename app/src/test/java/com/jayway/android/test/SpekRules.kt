package com.jayway.android.test

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import timber.log.Timber


class TimberRule(val tag: String) : Rule {
    override fun before() {
        Timber.plant(systemOutTree)
    }

    override fun after() {

    }

    private val systemOutTree = object : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            System.out.println("${this@TimberRule.tag}: $message")
            t?.let {
                System.out.println(t.stackTrace)
            }
        }
    }
}


object RxTestSchedulerRule : Rule {
    val testScheduler = TestScheduler()
    override fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
    }

    override fun after() {
        RxJavaPlugins.reset()
    }
}


object RxImmediateSchedulerRule : Rule {
    override fun before() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setErrorHandler { throwable ->
            System.out.println("Test: $throwable")             // io.reactivex.exceptions.OnErrorNotImplementedException
            System.out.println("  Test: cause: ${throwable.cause}")  // java.lang.Exception
            System.out.println("  ${throwable.message}")                     // "Test"
            System.out.println("  ${throwable.stackTrace}")
        }
    }

    override fun after() {
        RxJavaPlugins.reset()
    }
}