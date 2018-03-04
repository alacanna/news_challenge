package com.amandalacanna.newsapp.di.components

import android.app.Application
import com.amandalacanna.newsapp.application.NewsApplication
import com.amandalacanna.newsapp.di.modules.ActivityModule
import com.amandalacanna.newsapp.di.modules.ApiModule
import com.amandalacanna.newsapp.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityModule::class), (ApiModule::class)])
interface AppComponent {
    fun inject(application: NewsApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}