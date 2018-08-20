package com.jayway.android.test

import org.jetbrains.spek.api.dsl.SpecBody


/**
 * A [SpecBody] extension that defines a [SpecBody.beforeGroup] and [SpecBody.afterGroup] convenient
 * to use when testing, for setting up the equivalent of JUnit Test Rules.
 *
 * If applied outside the root group in a Spek test, it will apply the [Rule]s in the order they
 * are provided to this function, by executing their [Rule.before] as part of [SpecBody.beforeGroup]
 * and [Rule.after] as part of [SpecBody.afterGroup]
 *
 * @param rules The vararg list of [Rule] to apply. Each rule will be applied in the order provided.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun SpecBody.testRules(vararg rules: Rule) {
    beforeGroup {
        rules.forEach { it.before() }
    }
    afterGroup {
        rules.forEach { it.after() }
    }
}

interface Rule {
    fun before()
    fun after()
}