package com.amandalacanna.newsapp.features.news.adapter

import android.support.v7.widget.RecyclerView
import com.amandalacanna.data.Article
import com.amandalacanna.newsapp.R
import com.amandalacanna.newsapp.databinding.CardItemNewsBinding
import com.amandalacanna.newsapp.extensions.color
import com.amandalacanna.newsapp.features.news.viewmodel.NewsViewModel

class NewsViewHolder (private val binding: CardItemNewsBinding, private val viewModel: NewsViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun setArticle(article: Article) {
        binding.article = article
        setUrl(article.url)
    }

    private fun setUrl(url: String?) {
        url?.let {
            binding.txtUrl.text = "Read more on ".color(R.color.colorAccent, binding.root.context, true)
            binding.txtUrl.append(it.color(R.color.colorPrimary, binding.root.context))
        }
    }

    fun clickListener(article: Article) {
        binding.root.setOnClickListener {
            viewModel.clickNews(article)
        }
    }
}