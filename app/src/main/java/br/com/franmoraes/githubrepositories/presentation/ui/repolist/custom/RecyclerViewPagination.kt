package br.com.franmoraes.githubrepositories.presentation.ui.repolist.custom

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPagination(
    private val recyclerView: RecyclerView,
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    private var visibleItems: Int = 5
    private var endWithAuto = false

//    private var currentPage: Int = 0
//    private var previousTotalItemCount: Int = 0
//    private var startingPageIndex = 0
//    private var isLoading: Boolean = true

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            var lastVisibleItemPosition = 0
            var visibleItemCount = layoutManager.childCount
            var totalItemCount = layoutManager.itemCount

            if (layoutManager is LinearLayoutManager) {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is GridLayoutManager) {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            if(isLastPage()) return

            val itemCount = visibleItemCount + lastVisibleItemPosition + visibleItems
            if(itemCount >= totalItemCount) {
                if (!endWithAuto) {
                    endWithAuto = true
                    fetchMore()
                }
            } else {
                endWithAuto = false
            }
        }
    }

//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        var lastVisibleItemPosition = 0
//        var totalItemCount = recyclerViewManager?.itemCount ?: 0
//
//        if (recyclerViewManager is LinearLayoutManager) {
//            lastVisibleItemPosition = recyclerViewManager.findLastVisibleItemPosition()
//        } else if (recyclerViewManager is GridLayoutManager) {
//            lastVisibleItemPosition = recyclerViewManager.findLastVisibleItemPosition()
//        }
//
//        when {
//            (totalItemCount < previousTotalItemCount) -> {
//                currentPage = startingPageIndex
//                previousTotalItemCount = totalItemCount
//                if (totalItemCount == 0) isLoading = true
//            }
//            (isLoading && (totalItemCount > previousTotalItemCount)) -> {
//                isLoading = false
//                previousTotalItemCount = totalItemCount
//            }
//            !isLoading && (lastVisibleItemPosition + visibleItems) >= totalItemCount -> {
//                currentPage++
//                fetchMore()
//                isLoading = true
//            }
//        }
//    }

    abstract fun fetchMore()
    abstract fun isLastPage(): Boolean
}