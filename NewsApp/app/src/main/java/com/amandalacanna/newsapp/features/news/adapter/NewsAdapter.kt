package com.amandalacanna.newsapp.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.amandalacanna.data.Article
import com.amandalacanna.newsapp.databinding.CardItemNewsBinding
import com.amandalacanna.newsapp.di.AppInjector
import com.amandalacanna.newsapp.features.news.viewmodel.NewsViewModel
import java.util.ArrayList
import javax.inject.Inject


class NewsAdapter @Inject constructor(private val viewModel: NewsViewModel) : RecyclerView.Adapter<NewsViewHolder>() {

    var repositoryList: MutableList<Article> = ArrayList()

    init {
        AppInjector.dagger.inject(this)
    }

    fun setListRepository(listRepository: List<Article>) {
        this@NewsAdapter.repositoryList.addAll(listRepository)
        this@NewsAdapter.notifyItemInserted(itemCount - listRepository.size)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setArticle(repositoryList[position])
        holder.clickListener(repositoryList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardItemNewsBinding.inflate(layoutInflater, parent, false)

        return NewsViewHolder(binding, viewModel)
    }
}
