package com.jayway.example.github.repositories

import com.jayway.example.github.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_empty_section.*

class EmptyItem(val text: String) : Item() {

    override fun getLayout(): Int = R.layout.item_empty_section

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.empty_lbl.text = text
    }
}