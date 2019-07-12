package com.tasks.microblogging.ui.authordetails.domain.repository

import com.tasks.microblogging.data.remote.network.response.Post
import com.tasks.microblogging.ui.authordetails.data.remote.AuthorDetailsRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class AuthorDetailsRepositoryImp @Inject constructor(
    private val remoteDataSource: AuthorDetailsRemoteDataSource
) : AuthorDetailsRepository {
    override fun getAuthorPosts(authorId: Int, page: Int, limit: Int): Single<List<Post>> =
        remoteDataSource.getAuthorPosts(authorId = authorId, page = page, limit = limit)
}