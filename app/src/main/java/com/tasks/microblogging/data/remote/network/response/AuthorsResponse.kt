package com.tasks.microblogging.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("id")
    var authorID: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("avatarUrl")
    var avatarUrl: String,
    @SerializedName("address")
    var address: Address
)

data class Address(
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double
)