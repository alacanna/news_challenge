package com.amandalacanna.newsapp.di.modules

import com.amandalacanna.api.RestAPI
import com.amandalacanna.api.request.NewsRequest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApiModule {
    @Provides
    @Singleton
    internal fun provideRestAPI(): RestAPI {
        return RestAPI()
    }

    @Provides
    @Singleton
    internal fun provideNewsRequest(restAPI: RestAPI): NewsRequest {
        return restAPI.newsRequest
    }

}