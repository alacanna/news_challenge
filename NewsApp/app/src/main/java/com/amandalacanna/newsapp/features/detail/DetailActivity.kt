package com.amandalacanna.newsapp.features.detail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.amandalacanna.newsapp.R
import android.databinding.DataBindingUtil
import com.amandalacanna.data.Article
import com.amandalacanna.newsapp.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection

class DetailActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_ARTICLE = "article"

        fun startIntent(context: Context, parcelable: Parcelable?): Intent {
            return Intent(context, DetailActivity::class.java)
                    .putExtra(EXTRA_ARTICLE, parcelable)
        }
    }

    private val article by lazy { intent.getParcelableExtra<Article>(EXTRA_ARTICLE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)

        article?.let {
            article ->
            binding.article = article
            setImage(article, binding)
        }
    }

    private fun setImage(article: Article, binding: ActivityDetailBinding) {
        article.urlToImage?.let {
            Picasso.with(binding.root.context)
                    .load(article.urlToImage)
                    .into(binding.imgArticle)
        }
    }
}
