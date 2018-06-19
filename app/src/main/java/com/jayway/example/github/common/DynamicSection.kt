package com.jayway.example.github.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.xwray.groupie.Group
import com.xwray.groupie.Item
import com.xwray.groupie.NestedGroup
import java.util.ArrayList

@Suppress("unused")
/**
 * A group which has a list of contents and an optional header and footer.
 *
 * Converted to Kotlin.
 *
 * Ulrik: Adds the replace() method, which enables replacing individual items in item list - IF they are different
 */
class DynamicSection constructor(private var header: Group? = null, children: Collection<Group> = ArrayList()) :
    NestedGroup() {

    private var footer: Group? = null

    private var placeholder: Group? = null

    private val children = ArrayList<Group>()

    private var hideWhenEmpty = false

    private var isHeaderAndFooterVisible = true

    private var isPlaceholderVisible = false

    private val listUpdateCallback = object : ListUpdateCallback {

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(headerItemCount + position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(headerItemCount + position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            val headerItemCount = headerItemCount
            notifyItemMoved(headerItemCount + fromPosition, headerItemCount + toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(headerItemCount + position, count, payload)
        }
    }

    /**
     * Whether a section's contents are visually empty
     *
     * @return
     */
    protected val isEmpty: Boolean
        get() = children.isEmpty() || getItemCount(children) == 0

    private val bodyItemCount: Int
        get() = if (isPlaceholderVisible) placeholderItemCount else getItemCount(children)

    private val itemCountWithoutFooter: Int
        get() = bodyItemCount + headerItemCount

    private val headerCount: Int
        get() = if (header == null || !isHeaderAndFooterVisible) 0 else 1

    private val headerItemCount: Int
        get() = if (headerCount == 0) 0 else header!!.itemCount

    private val footerItemCount: Int
        get() = if (footerCount == 0) 0 else footer!!.itemCount

    private val footerCount: Int
        get() = if (footer == null || !isHeaderAndFooterVisible) 0 else 1

    private val placeholderCount: Int
        get() = if (isPlaceholderVisible) 1 else 0

    private val isHeaderShown: Boolean
        get() = headerCount > 0

    private val isFooterShown: Boolean
        get() = footerCount > 0

    private val isPlaceholderShown: Boolean
        get() = placeholderCount > 0

    private val placeholderItemCount: Int
        get() = if (isPlaceholderVisible && placeholder != null) {
            placeholder!!.itemCount
        } else 0

    constructor(children: Collection<Group>) : this(null, children) {}

    init {
        addAll(children)
    }

    override fun add(position: Int, group: Group) {
        super.add(position, group)
        children.add(position, group)
        val notifyPosition = headerItemCount + getItemCount(children.subList(0, position))
        notifyItemRangeInserted(notifyPosition, group.itemCount)
        refreshEmptyState()
    }

    override fun addAll(groups: Collection<Group>) {
        if (groups.isEmpty()) return
        super.addAll(groups)
        val position = itemCountWithoutFooter
        this.children.addAll(groups)
        notifyItemRangeInserted(position, getItemCount(groups))
        refreshEmptyState()
    }

    override fun addAll(position: Int, groups: Collection<Group>) {
        if (groups.isEmpty()) {
            return
        }

        super.addAll(position, groups)
        this.children.addAll(position, groups)

        val notifyPosition = headerItemCount + getItemCount(children.subList(0, position))
        notifyItemRangeInserted(notifyPosition, getItemCount(groups))
        refreshEmptyState()
    }

    override fun add(group: Group) {
        super.add(group)
        val position = itemCountWithoutFooter
        children.add(group)
        notifyItemRangeInserted(position, group.itemCount)
        refreshEmptyState()
    }

    override fun remove(group: Group) {
        super.remove(group)
        val position = getItemCountBeforeGroup(group)
        children.remove(group)
        notifyItemRangeRemoved(position, group.itemCount)
        refreshEmptyState()
    }

    override fun removeAll(groups: Collection<Group>) {
        if (groups.isEmpty()) {
            return
        }

        super.removeAll(groups)
        for (group in groups) {
            val position = getItemCountBeforeGroup(group)
            children.remove(group)
            notifyItemRangeRemoved(position, group.itemCount)
        }
        refreshEmptyState()
    }

    /**
     * Replace all existing body content and dispatch fine-grained change notifications to the
     * parent using DiffUtil.
     *
     *
     * Item comparisons are made using:
     * - Item.isSameAs(Item otherItem) (are items the same?)
     * - Item.equals() (are contents the same?)
     *
     *
     * If you don't customize getId() or isSameAs() and equals(), the default implementations will return false,
     * meaning your Group will consider every update a complete change of everything.
     *
     * @param newBodyGroups The new content of the section
     */
    fun update(newBodyGroups: Collection<Group>) {

        val oldBodyGroups = ArrayList(children)
        val oldBodyItemCount = getItemCount(oldBodyGroups)
        val newBodyItemCount = getItemCount(newBodyGroups)

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldBodyItemCount

            override fun getNewListSize(): Int = newBodyItemCount

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return newItem.isSameAs(oldItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return newItem == oldItem
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return oldItem.getChangePayload(newItem)
            }
        })

        super.removeAll(children)
        children.clear()
        children.addAll(newBodyGroups)
        super.addAll(newBodyGroups)

        diffResult.dispatchUpdatesTo(listUpdateCallback)
        if (newBodyItemCount == 0 || oldBodyItemCount == 0) {
            refreshEmptyState()
        }
    }

    fun replace(newBodyGroups: Collection<Group>) {
        val oldBodyGroups = ArrayList(children)
        val oldBodyItemCount = getItemCount(oldBodyGroups)
        val newBodyItemCount = getItemCount(newBodyGroups)

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldBodyItemCount

            override fun getNewListSize(): Int = newBodyItemCount

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return newItem.isSameAs(oldItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return newItem == oldItem
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = getItem(oldBodyGroups, oldItemPosition)
                val newItem = getItem(newBodyGroups, newItemPosition)
                return oldItem.getChangePayload(newItem)
            }
        })

        super.removeAll(children)
        children.clear()
        children.addAll(newBodyGroups)
        super.addAll(newBodyGroups)

        diffResult.dispatchUpdatesTo(listUpdateCallback)
        if (newBodyItemCount == 0 || oldBodyItemCount == 0) {
            refreshEmptyState()
        }
    }

    private fun getItem(groups: Collection<Group>, position: Int): Item<*> {
        var previousPosition = 0

        for (group in groups) {
            val size = group.itemCount
            if (size + previousPosition > position) {
                return group.getItem(position - previousPosition)
            }
            previousPosition += size
        }

        throw IndexOutOfBoundsException("Wanted item at " + position + " but there are only "
                                            + previousPosition + " items")
    }

    /**
     * Optional. Set a placeholder for when the section's body is empty.
     *
     *
     * If setHideWhenEmpty(true) is set, then the empty placeholder will not be shown.
     *
     * @param placeholder A placeholder to be shown when there is no body content
     */
    fun setPlaceholder(placeholder: Group) {

        if (this.placeholder != null) {
            removePlaceholder()
        }
        this.placeholder = placeholder
        refreshEmptyState()
    }

    fun removePlaceholder() {
        hidePlaceholder()
        this.placeholder = null
    }

    private fun showPlaceholder() {
        if (isPlaceholderVisible || placeholder == null) return
        isPlaceholderVisible = true
        notifyItemRangeInserted(headerItemCount, placeholder!!.itemCount)
    }

    private fun hidePlaceholder() {
        if (!isPlaceholderVisible || placeholder == null) return
        isPlaceholderVisible = false
        notifyItemRangeRemoved(headerItemCount, placeholder!!.itemCount)
    }

    private fun hideDecorations() {
        if (!isHeaderAndFooterVisible && !isPlaceholderVisible) return
        val count = headerItemCount + placeholderItemCount + footerItemCount
        isHeaderAndFooterVisible = false
        isPlaceholderVisible = false
        notifyItemRangeRemoved(0, count)
    }

    protected fun refreshEmptyState() {
        val isEmpty = isEmpty
        if (isEmpty) {
            if (hideWhenEmpty) {
                hideDecorations()
            } else {
                showPlaceholder()
                showHeadersAndFooters()
            }
        } else {
            hidePlaceholder()
            showHeadersAndFooters()
        }
    }

    private fun showHeadersAndFooters() {
        if (isHeaderAndFooterVisible) return
        isHeaderAndFooterVisible = true
        notifyItemRangeInserted(0, headerItemCount)
        notifyItemRangeInserted(itemCountWithoutFooter, footerItemCount)
    }

    override fun getGroup(position: Int): Group {
        var positionLocal = position
        if (isHeaderShown && positionLocal == 0) return header!! //FIXME is this okay to do (!! I mean)
        positionLocal -= headerCount
        if (isPlaceholderShown && positionLocal == 0) return placeholder!! //FIXME is this okay to do (!! I mean)
        positionLocal -= placeholderCount
        if (positionLocal == children.size) {
            return if (isFooterShown) {
                footer!! //FIXME is this okay to do (!! I mean)
            } else {
                throw IndexOutOfBoundsException(("Wanted group at position " + positionLocal +
                    " but there are only " + groupCount + " groups"))
            }
        } else {
            return children[positionLocal]
        }
    }

    override fun getGroupCount(): Int {
        return headerCount + footerCount + placeholderCount + children.size
    }

    override fun getPosition(group: Group): Int {
        var count = 0
        if (isHeaderShown) {
            if (group === header) return count
        }
        count += headerCount
        if (isPlaceholderShown) {
            if (group === placeholder) return count
        }
        count += placeholderCount

        val index = children.indexOf(group)
        if (index >= 0) return count + index
        count += children.size

        if (isFooterShown) {
            if (footer === group) {
                return count
            }
        }

        return -1
    }

    fun setHeader(header: Group) {
        val previousHeaderItemCount = headerItemCount
        this.header = header
        notifyHeaderItemsChanged(previousHeaderItemCount)
    }

    fun removeHeader() {
        val previousHeaderItemCount = headerItemCount
        this.header = null
        notifyHeaderItemsChanged(previousHeaderItemCount)
    }

    private fun notifyHeaderItemsChanged(previousHeaderItemCount: Int) {
        val newHeaderItemCount = headerItemCount
        if (previousHeaderItemCount > 0) {
            notifyItemRangeRemoved(0, previousHeaderItemCount)
        }
        if (newHeaderItemCount > 0) {
            notifyItemRangeInserted(0, newHeaderItemCount)
        }
    }


    fun setFooter(footer: Group) {
        val previousFooterItemCount = footerItemCount
        this.footer = footer
        notifyFooterItemsChanged(previousFooterItemCount)
    }

    fun removeFooter() {
        val previousFooterItemCount = footerItemCount
        this.footer = null
        notifyFooterItemsChanged(previousFooterItemCount)
    }

    private fun notifyFooterItemsChanged(previousFooterItemCount: Int) {
        val newFooterItemCount = footerItemCount
        if (previousFooterItemCount > 0) {
            notifyItemRangeRemoved(itemCountWithoutFooter, previousFooterItemCount)
        }
        if (newFooterItemCount > 0) {
            notifyItemRangeInserted(itemCountWithoutFooter, newFooterItemCount)
        }
    }

    fun setHideWhenEmpty(hide: Boolean) {
        if (hideWhenEmpty == hide) return
        hideWhenEmpty = hide
        refreshEmptyState()
    }

    override fun onItemInserted(group: Group, position: Int) {
        super.onItemInserted(group, position)
        refreshEmptyState()
    }

    override fun onItemRemoved(group: Group, position: Int) {
        super.onItemRemoved(group, position)
        refreshEmptyState()
    }

    override fun onItemRangeInserted(group: Group, positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(group, positionStart, itemCount)
        refreshEmptyState()
    }

    override fun onItemRangeRemoved(group: Group, positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(group, positionStart, itemCount)
        refreshEmptyState()
    }
}
