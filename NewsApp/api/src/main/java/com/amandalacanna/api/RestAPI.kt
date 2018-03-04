package com.amandalacanna.api

import com.amandalacanna.api.request.NewsRequest
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestAPI(httpUrl: HttpUrl? = HttpUrl.parse(BuildConfig.SERVICE_URL)) {

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        val log = HttpLoggingInterceptor()
        log.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        log
    }

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)!!

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(httpUrl!!)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

    val repositoryRequest: NewsRequest by lazy {
        retrofit.create(NewsRequest::class.java)
    }
}
