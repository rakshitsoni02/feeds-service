package com.blacklane.feeds.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blacklane.feeds.R
import com.blacklane.feeds.model.vo.Feed
import com.blacklane.feeds.viewmodel.FeedsViewModel
import com.blacklane.shared.app.BaseFragment
import com.blacklane.shared.lifecycle.DataObserver
import com.blacklane.shared.utils.view.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.f_feeds_list.*
import kotlinx.android.synthetic.main.progress_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class FeedsListingFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: FeedsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_feeds_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) return
        val adapter = FeedsAdapter()
        f_container_feeds_listing.adapter = adapter
        viewModel.getFeedsObserver().observe(DataObserver.ResourceObserver(
            loadingState = {
                progressViewFeeds.isVisible = it
            },
            data = ::updateFeeds,
            error = {
                toast(it.text(requireActivity()))
            }
        ))


        fun refreshState() {
            when (adapter.itemCount) {
                0 -> {
                    progressViewFeeds.isVisible = false
                    f_feeds_empty_view.isVisible = true
                    f_container_feeds_listing.isVisible = false
                }
                else -> {
                    progressViewFeeds.isVisible = false
                    f_feeds_empty_view.isVisible = false
                    f_container_feeds_listing.isVisible = true
                }
            }
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() = refreshState()
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = refreshState()
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = refreshState()
        })
        viewModel.syncFeeds()
    }

    private fun updateFeeds(feeds: List<Feed>) {
        (f_container_feeds_listing.adapter as FeedsAdapter).submitList(feeds)
    }

    companion object {
        fun newInstance() = FeedsListingFragment()
    }
}