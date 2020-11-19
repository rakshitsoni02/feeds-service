package com.blacklane.feeds.viewmodel

import com.blacklane.feeds.model.repo.FeedsRepository
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.lifecycle.ViewState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@RunWith(JUnit4::class)
class FeedsViewModelTest {

    @Mock
    lateinit var feedsRepository: FeedsRepository

    @Inject
    lateinit var viewModel: FeedsViewModel

    @Mock
    lateinit var externalScope: CoroutineScope

    @Mock
    lateinit var mainDispatcher: CoroutineDispatcher

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = FeedsViewModel(feedsRepository, externalScope, mainDispatcher)
    }


//    @Test
//    fun syncFeeds_test() {
//        val mockFlow = mock<Flow<ViewState<List<Feed>>>>()
//        whenever(feedsRepository.getFeedsArticles()) doReturn mockFlow
//        viewModel.syncFeeds()
//
//    }
}