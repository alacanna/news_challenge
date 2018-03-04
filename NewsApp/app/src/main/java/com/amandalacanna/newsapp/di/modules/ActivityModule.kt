package com.amandalacanna.newsapp.di.modules

import com.amandalacanna.newsapp.features.detail.DetailActivity
import com.amandalacanna.newsapp.features.news.NewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contribuiteNewsActivity(): NewsActivity

    @ContributesAndroidInjector
    internal abstract fun contribuiteDetailActivity(): DetailActivity
}