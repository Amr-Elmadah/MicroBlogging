package com.tasks.microblogging.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    var id: Int,
    @SerializedName("date")
    var date: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("avatarUrl")
    var avatarUrl: Address,
    @SerializedName("postId")
    var postId: Address
)

data class CommentsParams(
    var postId: Int,
    var page: Int,
    var limit: Int,
    var sort: String,
    var order: String
)