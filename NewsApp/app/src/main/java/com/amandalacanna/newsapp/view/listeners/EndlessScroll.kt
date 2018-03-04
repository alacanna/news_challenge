package com.amandalacanna.newsapp.view.listeners

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.amandalacanna.newsapp.features.news.viewmodel.NewsViewModel

class EndlessScroll(private val viewModel: NewsViewModel) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        val layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (totalItemCount > 1) {
            if (lastVisibleItem == totalItemCount - 1) {
                viewModel.inputs.getNews()
            }
        }
    }
}