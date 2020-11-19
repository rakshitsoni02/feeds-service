package com.blacklane.feeds.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blacklane.feeds.R
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.shared.utils.view.inflate
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedsAdapter : ListAdapter<Feed, FeedsAdapter.FeedItemHolder>(DIFF_CALLBACK) {

    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FeedItemHolder(parent.inflate(R.layout.item_feed))

    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(feedItemHolder: FeedItemHolder, position: Int) =
        feedItemHolder.bind(getItem(position))

    /**
     * View Holder Pattern
     */
    class FeedItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(recipe: Feed) = with(itemView) {
            tv_feed_title.text = recipe.title
            tv_feed_description.text = recipe.body
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Feed>() {
            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Feed,
                newItem: Feed
            ): Boolean = oldItem == newItem
        }
    }
}