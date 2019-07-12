package com.tasks.microblogging.ui.postcomments.domain.repository

import com.tasks.microblogging.data.remote.network.response.Comment
import com.tasks.microblogging.ui.postcomments.data.remote.PostCommentsRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class PostCommentsRepositoryImp @Inject constructor(
    private val remoteDataSource: PostCommentsRemoteDataSource
) : PostCommentsRepository {
    override fun getPostComments(
        postId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): Single<List<Comment>> =
        remoteDataSource.getPostComments(postId = postId, page = page, limit = limit, sort = sort, order = order)
}