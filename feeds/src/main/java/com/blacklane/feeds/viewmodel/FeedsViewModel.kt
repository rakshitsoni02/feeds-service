package com.blacklane.feeds.viewmodel

import com.blacklane.feeds.model.repo.FeedsRepository
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.lifecycle.ApplicationScope
import com.blacklane.shared.lifecycle.DataObserver
import com.blacklane.shared.lifecycle.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedsViewModel @Inject constructor(
    private val feedsRepository: FeedsRepository,
    @ApplicationScope private val externalScope: CoroutineScope,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {
    private val feedsObserver: DataObserver<List<Feed>> = DataObserver()
    var job: Job? = null
    fun getFeedsObserver(): DataObserver<List<Feed>> {
        return feedsObserver
    }

    fun syncFeeds() {
        job?.start()
        job = externalScope.launch(mainDispatcher) {
            feedsRepository.getFeedsArticles().collect {
                feedsObserver.value = it
            }
        }
    }
}
