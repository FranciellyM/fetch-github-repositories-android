package br.com.franmoraes.githubrepositories.presentation.ui.repolist.custom

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPagination(
    private val recyclerView: RecyclerView,
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    private var visibleItems: Int = 4
    private var endWithAuto: Boolean = false

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (isLastPage()) return

        var lastVisibleItemPosition = 0
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is GridLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        }

        val itemCount = visibleItemCount + lastVisibleItemPosition + visibleItems
        if (itemCount >= totalItemCount) {
            if (!endWithAuto) {
                endWithAuto = true
                fetchMore()
            }
        } else {
            endWithAuto = false
        }
    }

    abstract fun fetchMore()
    abstract fun isLastPage(): Boolean
}