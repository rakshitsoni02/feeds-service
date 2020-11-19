package com.blacklane.feeds.api

import com.blacklane.feeds.model.vo.Feed
import retrofit2.Response
import retrofit2.http.GET

/**
 * Describes endpoints to fetch the feeds from API.
 */
interface FeedsService {

    /**
     * Get fresh feeds.
     */
    @GET("posts")
    suspend fun getFeeds(): Response<List<Feed>>

}
