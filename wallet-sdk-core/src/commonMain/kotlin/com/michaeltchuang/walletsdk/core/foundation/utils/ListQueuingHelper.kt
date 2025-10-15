package com.michaeltchuang.walletsdk.core.foundation.utils

import kotlin.properties.Delegates

open class ListQueuingHelper<E, D> {
    val currentItem: E?
        get() = _currentItem

    protected open val totalItemCount
        get() = enqueuedItemCount
    protected open val currentItemIndex
        get() = dequeuedItemList.size + 1

    protected val dequeuedItemList = mutableListOf<D?>()
    protected var enqueuedItemCount = -1
    private val enqueuedItemList = mutableListOf<E>()
    private var _currentItem: E? by Delegates.observable(null) { _, _, newValue ->
        if (newValue != null) {
            listener?.onNextItemToBeDequeued(
                item = newValue,
                // list index starts from zero, so we need to add 1 manually
                currentItemIndex = currentItemIndex,
                totalItemCount = totalItemCount,
            )
        }
    }

    protected var listener: Listener<E, D>? = null

    private val areAllItemsDequeued: Boolean
        get() = enqueuedItemCount == dequeuedItemList.size && enqueuedItemCount != -1

    fun initListener(listener: Listener<E, D>) {
        this.listener = listener
    }

    open fun initItemsToBeEnqueued(enqueuedItems: List<E>) {
        clearCachedData()
        enqueuedItemList.addAll(enqueuedItems)
        enqueuedItemCount = enqueuedItemList.size
        dequeueFirstItem()
    }

    fun cacheDequeuedItem(dequeuedItem: D?) {
        dequeuedItemList.add(dequeuedItem)
        if (areAllItemsDequeued) {
            listener?.onAllItemsDequeued(dequeuedItemList.toList())
            clearCachedData()
            return
        }
        dequeueFirstItem()
    }

    fun clearCachedData() {
        dequeuedItemList.clear()
        enqueuedItemList.clear()
        enqueuedItemCount = -1
        _currentItem = null
    }

    fun requeueCurrentItem() {
        _currentItem = currentItem
    }

    private fun dequeueFirstItem() {
        _currentItem = enqueuedItemList.removeFirstOrNull()
    }

    interface Listener<E, D> {
        fun onAllItemsDequeued(dequeuedItemList: List<D?>)

        fun onNextItemToBeDequeued(
            item: E,
            currentItemIndex: Int,
            totalItemCount: Int,
        )
    }
}
