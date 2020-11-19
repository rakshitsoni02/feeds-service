package com.blacklane.feeds.viewmodel

import com.blacklane.feeds.model.repo.FeedsRepository
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.lifecycle.ApplicationScope
import com.blacklane.shared.lifecycle.DataObserver
import com.blacklane.shared.lifecycle.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedsViewModel @Inject constructor(
    private val feedsRepository: FeedsRepository,
    @ApplicationScope private val externalScope: CoroutineScope,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {
    /**
     * observer gives call back to the UI with respect to current state
     */
    private val feedsObserver: DataObserver<List<Feed>> = DataObserver()

    fun getFeedsObserver(): DataObserver<List<Feed>> {
        return feedsObserver
    }

    /**
     * fetch the latest feeds from web service
     */
    fun syncFeeds() {
        externalScope.launch(mainDispatcher) {
            feedsRepository.getFeedsArticles().collect {
                feedsObserver.value = it
            }
        }
    }

    /**
     * checking matching feeds for given text
     */
    fun onSearchTextChanges(newText: CharSequence) {
        externalScope.launch(mainDispatcher) {
            feedsRepository.searchFeedForQuery(textQuery = newText).collect {
                feedsObserver.value = it
            }
        }
    }
}
