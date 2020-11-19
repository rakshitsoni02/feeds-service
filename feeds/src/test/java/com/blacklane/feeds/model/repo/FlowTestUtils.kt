package com.blacklane.feeds.model.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals

/**
 * Asserts only the [expected] items by just taking that many from the stream
 */
suspend fun <T> Flow<T>.assertItems(vararg expected: T) {
    assertEquals(expected.toList(), this.take(expected.size).toList())
}