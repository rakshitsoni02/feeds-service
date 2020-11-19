package com.blacklane.feeds.model.vo

import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)