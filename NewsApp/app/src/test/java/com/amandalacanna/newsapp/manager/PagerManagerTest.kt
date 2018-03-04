package com.amandalacanna.newsapp.manager

import junit.framework.Assert
import org.junit.Test

class PagerManagerTest {

    private val pagerManager: PagerManager = PagerManager(50)

    @Test
    fun shouldGoToNextPage() {
        Assert.assertEquals(1, pagerManager.nextPage())
        Assert.assertEquals(2, pagerManager.nextPage())
        Assert.assertEquals(3, pagerManager.nextPage())
    }

    @Test
    fun shouldStartToInicialPage() {
        pagerManager.totalResults = 0
        Assert.assertEquals(1, pagerManager.nextPage())
    }


    @Test
    fun shouldStopInMaxPage() {
        pagerManager.nextPage()
        pagerManager.nextPage()
        pagerManager.nextPage()
        pagerManager.nextPage()
        pagerManager.nextPage()

        Assert.assertEquals(-1, pagerManager.nextPage())
    }

    @Test
    fun shouldGoToPreviousPage() {
        pagerManager.nextPage()
        pagerManager.nextPage()
        pagerManager.nextPage()

        Assert.assertEquals(2, pagerManager.previousPage())
    }

    @Test
    fun shouldStopInTheFistPage() {
        pagerManager.nextPage()

        // back
        pagerManager.previousPage()
        pagerManager.previousPage()

        Assert.assertEquals(0, pagerManager.previousPage())
    }


}