package com.amandalacanna.newsapp.features.news.viewmodel

import com.amandalacanna.api.request.NewsRequest
import com.amandalacanna.data.Article
import com.amandalacanna.newsapp.manager.PAGE_SIZE
import com.amandalacanna.newsapp.manager.PagerManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

interface NewsViewModelInputs {
    fun getNews()
    fun clickNews(data: Article)
}

interface NewsViewModelModelOutputs {
    val isLoading: Observable<Boolean>
    val listArticles: Observable<List<Article>>
    val hasError: Observable<String>
    val newsListener: Observable<Article>
}

interface NewsViewModelModelType {
    val inputs: NewsViewModelInputs
    val outputs: NewsViewModelModelOutputs
}
@Singleton
open class NewsViewModel @Inject constructor(private val request: NewsRequest) : NewsViewModelModelType, NewsViewModelInputs, NewsViewModelModelOutputs {

    val pagerManager: PagerManager = PagerManager()

    // Initialize inputs and outputs
    override val inputs: NewsViewModelInputs
        get() = this

    override val outputs: NewsViewModelModelOutputs
        get() = this

    // Outputs implementations
    private val isLoadingSubject = PublishSubject.create<Boolean>()
    override val isLoading: Observable<Boolean>
        get() = isLoadingSubject

    private val listArticlesSubject = PublishSubject.create<List<Article>>()
    override val listArticles: Observable<List<Article>>
        get() = listArticlesSubject

    private val hasErrorSubject = PublishSubject.create<String>()
    override val hasError: Observable<String>
        get() = hasErrorSubject

    private val newsListenerSubject = PublishSubject.create<Article>()
    override val newsListener: Observable<Article>
        get() = newsListenerSubject

    override fun getNews() {
        request.getNews(pagerManager.nextPage(), PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadingSubject.onNext(true) }
                .doOnError { isLoadingSubject.onNext(false) }
                .doOnComplete { isLoadingSubject.onNext(false) }
                .subscribe(
                        {
                            pagerManager.totalResults = it.totalResults
                            listArticlesSubject.onNext(it.articles)
                        }, { _ ->
                    hasErrorSubject.onNext("Ops, an error occured!")
                })
    }

    override fun clickNews(data: Article) {
        newsListenerSubject.onNext(data)
    }
}