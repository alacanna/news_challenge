package com.amandalacanna.newsapp.features.news.viewmodel

import com.amandalacanna.api.request.NewsRequest
import com.amandalacanna.api.response.NewsResponse
import com.amandalacanna.newsapp.manager.PAGE_SIZE
import com.amandalacanna.newsapp.manager.PagerManager
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {
    private val immediate = object : Scheduler() {
        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    private val mockNewsResult: String get() {
        return "{  \n" +
                "   \"status\":\"ok\",\n" +
                "   \"totalResults\":525,\n" +
                "   \"articles\":[  \n" +
                "      {  \n" +
                "         \"source\":{  \n" +
                "            \"id\":null,\n" +
                "            \"name\":\"Codeproject.com\"\n" +
                "         },\n" +
                "         \"author\":\"David A. Gray\",\n" +
                "         \"title\":\"Configuration File Formatting Oddities: A Short Case Study\",\n" +
                "         \"description\":\"URL Encoded Text is URL Encoded Text, wherever you find it, even if it happens to be in and old school Windows INI file.\",\n" +
                "         \"url\":\"https://www.codeproject.com/Tips/1224052/Configuration-File-Formatting-Oddities-A-Short-Cas\",\n" +
                "         \"urlToImage\":null,\n" +
                "         \"publishedAt\":\"2018-01-06T07:26:00Z\"\n" +
                "      },\n" +
                "      {  \n" +
                "         \"source\":{  \n" +
                "            \"id\":null,\n" +
                "            \"name\":\"Salesforce.com\"\n" +
                "         },\n" +
                "         \"author\":null,\n" +
                "         \"title\":\"Plus (+) sign being URL encoded when saved to prospect record\",\n" +
                "         \"description\":\"#In Review# Including a plus sign (+) in a prospect field value can result in it being encoded as a space when saved to the prospect record.\",\n" +
                "         \"url\":\"https://success.salesforce.com/issues_view?id=a1p3A0000018BjPQAU\",\n" +
                "         \"urlToImage\":null,\n" +
                "         \"publishedAt\":\"2018-02-06T17:53:26Z\"\n" +
                "      },\n" +
                "      {  \n" +
                "         \"source\":{  \n" +
                "            \"id\":null,\n" +
                "            \"name\":\"Stackoverflow.com\"\n" +
                "         },\n" +
                "         \"author\":\"greedyLump\",\n" +
                "         \"title\":\"asp-page tag helper inserts encoded url inside form\",\n" +
                "         \"description\":\"I have a razor page with two page handlers: public IActionResult OnGetDisTest(int Id) { discussion = _service.GetDiscussion(Id, null); return Page(); } public IActionResult OnGetDisTestThread( int Id) { discussion = _service.GetDiscussion(null, Id); return Pa…\",\n" +
                "         \"url\":\"https://stackoverflow.com/questions/48710948/asp-page-tag-helper-inserts-encoded-url-inside-form\",\n" +
                "         \"urlToImage\":\"https://cdn.sstatic.net/Sites/stackoverflow/img/apple-touch-icon@2.png?v=73d79a89bded\",\n" +
                "         \"publishedAt\":\"2018-02-09T17:16:03Z\"\n" +
                "      }\n" +
                "   ]\n" +
                "}"
    }

    @Before
    fun setUp() {
        setUpRxThreadControl()
    }
    @Mock
    lateinit var newsRequest: NewsRequest

    @InjectMocks
    lateinit var viewModel: NewsViewModel

    @InjectMocks
    lateinit var pagerManager: PagerManager


    var repositoryMock = Gson().fromJson<NewsResponse>(mockNewsResult, NewsResponse::class.java)

    @Test
    fun shouldStartShowingProgressAndFinishHideIt() {
        Mockito.`when`(newsRequest.getNews(pagerManager.nextPage(), PAGE_SIZE)).thenReturn(Observable.just(repositoryMock))

        val subscriber = viewModel.outputs.isLoading.test()
        viewModel.inputs.getNews()

        subscriber.assertValues(true, false)
    }

    @Test
    fun shouldShowRequestErrorMessageWhenFetchDataFails() {
        Mockito.`when`(newsRequest.getNews(pagerManager.nextPage(), PAGE_SIZE))
                .thenReturn(Observable.unsafeCreate<NewsResponse> { subscriber -> subscriber.onError(RuntimeException()) })

        val errorSubscriber = viewModel.outputs.hasError.test()
        viewModel.inputs.getNews()

        errorSubscriber.assertValue { "Ops, an error occured!" == it }
    }

    @Test
    fun shouldInitRepositoryList() {
        Mockito.`when`(newsRequest.getNews(pagerManager.nextPage(), PAGE_SIZE)).thenReturn(Observable.just(repositoryMock))

        val listSubscriber = viewModel.outputs.listArticles.test()
        viewModel.inputs.getNews()

        listSubscriber.assertOf {
            val listRepository = listSubscriber.values()[0]
            Assert.assertEquals(listRepository, repositoryMock.articles)
        }
    }

    private fun setUpRxThreadControl() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}