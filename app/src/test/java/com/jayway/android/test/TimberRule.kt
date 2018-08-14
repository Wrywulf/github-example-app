package com.jayway.android.test

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import timber.log.Timber


/**
 * A rule that plants a Timber.Tree which logs to System.out
 */
class TimberRule(val tag : String) : TestRule {

    val systemOutTree = object : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            System.out.println("${this@TimberRule.tag}: $message")
            t?.let {
                System.out.println(t.stackTrace)
            }
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Timber.plant(systemOutTree)
                base.evaluate()
            }
        }
    }
}