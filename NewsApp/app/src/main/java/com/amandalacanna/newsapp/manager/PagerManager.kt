package com.amandalacanna.newsapp.manager

const val PAGE_SIZE = 10

open class PagerManager (var totalResults: Int) {
    private val INITIAL_PAGE = 0
    private val RESULT_FINISH_PAGES = -1


    private var _page = INITIAL_PAGE

    val maxPage by lazy {
        if (totalResults.rem(PAGE_SIZE) == 0) {
            totalResults.div(PAGE_SIZE)
        } else {
            totalResults.div(PAGE_SIZE).plus(1)
        }
    }

    fun nextPage(): Int {
        return if ((_page < maxPage)) {
            ++_page
        } else {
            RESULT_FINISH_PAGES
        }
    }

    fun previousPage(): Int {
        return if (_page > 0 ) {
            --_page
        } else {
            INITIAL_PAGE
        }
    }
}
