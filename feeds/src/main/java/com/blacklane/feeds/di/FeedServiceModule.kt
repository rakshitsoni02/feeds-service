package com.blacklane.feeds.di

import com.blacklane.feeds.api.FeedsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FeedServiceModule {

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): FeedsService =
        retrofit.create(FeedsService::class.java)
}