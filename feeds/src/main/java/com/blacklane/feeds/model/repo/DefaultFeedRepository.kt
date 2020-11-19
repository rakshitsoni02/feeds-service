package com.blacklane.feeds.model.repo

import com.blacklane.feeds.R
import com.blacklane.feeds.api.FeedsService
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.lifecycle.ViewState
import com.blacklane.shared.utils.strings.StringProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 */
interface FeedsRepository {

    /**
     * fetch the list of feed items from web service
     */
    fun getFeedsArticles(): Flow<ViewState<List<Feed>>>

    fun searchFeedForQuery(textQuery: CharSequence): Flow<ViewState<List<Feed>>>
}

@Singleton
class DefaultFeedsRepository @Inject constructor(
    private val feedsService: FeedsService
) : FeedsRepository {

    private val cachedFeeds: MutableList<Feed> = mutableListOf()

    override fun getFeedsArticles(): Flow<ViewState<List<Feed>>> = flow {
        // 1. Start with loading
        emit(ViewState.loading<List<Feed>>())
        try {
            // 2. Try to fetch fresh feeds from web if exist
            // It should be parse in different VO(when it'a complex json) for now it's used common
            val latestFeeds = feedsService.getFeeds().body()
            cachedFeeds.clear()
            cachedFeeds.addAll(latestFeeds ?: emptyList())
        } catch (e: Throwable) {
            emit(ViewState.error(StringProvider.of(R.string.feed_error_label)))
            return@flow
        }
        // 3. update latest feeds to UI(cached feeds always single source of truth)
        emit(ViewState.success(cachedFeeds.toList()))
    }
        .flowOn(Dispatchers.IO)

    override fun searchFeedForQuery(textQuery: CharSequence): Flow<ViewState<List<Feed>>> = flow {
        // 1. Start with loading
        emit(ViewState.loading<List<Feed>>())
        //2. search for feeds in cache
        val result = cachedFeeds.filter {
            it.title.contains(textQuery, ignoreCase = true)
        }
        // 3.update resultant feeds
        emit(ViewState.success(result))
    }
}


@Module
@InstallIn(ApplicationComponent::class)
interface FeedsRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds
    fun it(it: DefaultFeedsRepository): FeedsRepository
}