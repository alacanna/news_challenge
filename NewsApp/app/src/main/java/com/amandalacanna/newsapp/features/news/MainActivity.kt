package com.amandalacanna.newsapp.features.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.amandalacanna.newsapp.R
import com.amandalacanna.newsapp.features.news.adapter.NewsAdapter
import com.amandalacanna.newsapp.features.news.viewmodel.NewsViewModel
import com.amandalacanna.newsapp.isVisible
import com.amandalacanna.newsapp.view.listeners.EndlessScroll
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private val disposables by lazy { CompositeDisposable() }

    private val txtPlaceHolder by lazy {
        txt_place_holder.setOnClickListener {
            txt_place_holder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.getNews()
        }

        txt_place_holder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (newsAdapter.repositoryList.isEmpty()) {
            progressBar.visibility = View.VISIBLE
            viewModel.inputs.getNews()
        }
    }

    override fun onStart() {
        super.onStart()
        bindOutputs()
        initListView()
    }
    override fun onStop() {
        super.onStop()
        disposables.clear()
    }


    private fun bindOutputs() = with(viewModel.outputs) {
        val listArticlesDisposable = listArticles.subscribe {
            newsAdapter.setListRepository(it)
        }

        val isLoadingDisposable = isLoading.subscribe { showProgress ->
            progressBar.isVisible(showProgress)
        }

        val hasErrorDisposable = hasError.subscribe({
            messsage ->
            Toast.makeText(baseContext, messsage, Toast.LENGTH_LONG).show()
        })

        val newsListenerDisposable = newsListener.subscribe {
            repositoryData ->
//           TODO goToAfterClick
        }

        disposables.addAll(listArticlesDisposable,
                isLoadingDisposable,
                hasErrorDisposable,
                newsListenerDisposable)
    }

    private fun initListView() {
        list_repository.layoutManager = list_repository.layoutManager
        (list_repository.layoutManager as GridLayoutManager).spanCount = 2

        list_repository.adapter = newsAdapter
        list_repository.addOnScrollListener(EndlessScroll(viewModel))
    }


}
