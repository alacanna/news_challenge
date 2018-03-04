package com.amandalacanna.newsapp.di.modules

import com.amandalacanna.newsapp.features.news.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contribuiteSplashActivity(): MainActivity
}