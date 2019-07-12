package com.tasks.microblogging.ui.authordetails.data.remote

import com.tasks.microblogging.data.remote.network.response.Post
import com.tasks.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import io.reactivex.Single
import javax.inject.Inject

class AuthorDetailsRemoteDataSource @Inject constructor(private val microBloggingAPI: MicroBloggingAPI) {
    fun getAuthorPosts(authorId: Int, page: Int, limit: Int, sort: String, order: String): Single<List<Post>> =
        microBloggingAPI.loadAuthorPosts(authorId = authorId, page = page, limit = limit, sort = sort, order = order)
}