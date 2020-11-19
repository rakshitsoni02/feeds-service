package com.blacklane.feeds.model.repo

import com.blacklane.feeds.api.FeedsService
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.lifecycle.ViewState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class FeedsRepositoryTest {

    @Mock
    lateinit var feedsService: FeedsService

    @InjectMocks
    lateinit var feedsRepository: DefaultFeedsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `get feeds articles from web when internet available`() = runBlocking {
        // GIVEN
        val fetchedFeeds = listOf(
            Feed(title = "title 1", body = "body 1", userId = -1, id = -1),
            Feed(title = "title 2", body = "body 2", userId = -2, id = -2)
        )

        val response = Response.success(fetchedFeeds)

        // WHEN
        whenever(feedsService.getFeeds()) doReturn response

        // THEN
        feedsRepository.getFeedsArticles().assertItems(
            ViewState.loading(),
            ViewState.success(fetchedFeeds)
        )
    }

    @Test
    fun `get feeds articles from cache for given search title text`() = runBlocking {
        feedsRepository.searchFeedForQuery("title").assertItems(
            ViewState.loading(),
            ViewState.success(emptyList())
        )
    }

}