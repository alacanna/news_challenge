package com.amandalacanna.newsapp.features.news.adapter

import android.support.v7.widget.RecyclerView
import com.amandalacanna.data.Article
import com.amandalacanna.newsapp.databinding.CardItemNewsBinding
import com.amandalacanna.newsapp.features.news.viewmodel.NewsViewModel

class NewsViewHolder (private val binding: CardItemNewsBinding, private val viewModel: NewsViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun setArticle(article: Article) {
        binding.article = article
    }

    fun clickListener(article: Article) {
        binding.root.setOnClickListener {
            viewModel.clickNews(article)
        }
    }
}