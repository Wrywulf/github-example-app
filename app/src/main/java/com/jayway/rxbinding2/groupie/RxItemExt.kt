package com.jayway.rxbinding2.groupie

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.Observable

/**
 * Create an observable which emits on [GroupAdapter] click events.
 *
 * *Warning:* The created observable keeps a strong reference to [GroupAdapter]. Unsubscribe
 * to free this reference.
 *
 * *Warning:* The created observable uses [GroupAdapter.setOnItemClickListener] to observe
 * clicks. Only one observable can be used for a view at a time.
 */
fun GroupAdapter<out ViewHolder>.clicks(): Observable<Item<out ViewHolder>> {
    return RxItem.clicks(this)
}