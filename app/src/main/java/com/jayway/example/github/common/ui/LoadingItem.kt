package com.jayway.example.github.common.ui

import com.jayway.example.github.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class LoadingItem : Item() {

    override fun getLayout(): Int = R.layout.item_progress_spinner

    override fun bind(viewHolder: ViewHolder, position: Int) {}
}