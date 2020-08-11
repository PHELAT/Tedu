package com.phelat.tedu.sdkextensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.onReachTheEnd(crossinline block: () -> Unit) {
    val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val visibleItemCount: Int = requireNotNull(layoutManager).childCount
            val totalItemCount: Int = requireNotNull(layoutManager).itemCount
            val pastVisibleItems: Int = (layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
            if (pastVisibleItems + visibleItemCount + 2 >= totalItemCount) {
                post { block.invoke() }
            }
        }
    }
    addOnScrollListener(scrollListener)
}