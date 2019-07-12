package com.tasks.microblogging.ui.authordetails.domain.repository

import com.tasks.microblogging.data.remote.network.response.Post
import io.reactivex.Single

interface AuthorDetailsRepository {
    fun getAuthorPosts(authorId: Int, page: Int, limit: Int, sort: String, order: String): Single<List<Post>>
}