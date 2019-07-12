package com.tasks.microblogging.ui.postcomments.injection

import com.tasks.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import com.tasks.microblogging.ui.postcomments.data.remote.PostCommentsRemoteDataSource
import com.tasks.microblogging.ui.postcomments.domain.interactor.GetPostCommentsUseCase
import com.tasks.microblogging.ui.postcomments.domain.repository.PostCommentsRepository
import com.tasks.microblogging.ui.postcomments.domain.repository.PostCommentsRepositoryImp
import dagger.Module
import dagger.Provides

@Module
class PostCommentsModule {
    @Provides
    fun providePostCommentsRemoteDataSource(microBloggingAPI: MicroBloggingAPI) =
        PostCommentsRemoteDataSource(microBloggingAPI = microBloggingAPI)

    @Provides
    fun providePostCommentsRepository(
        remoteDataSource: PostCommentsRemoteDataSource
    ): PostCommentsRepository =
        PostCommentsRepositoryImp(remoteDataSource)

    @Provides
    fun provideGetAuthorPostsUseCase(repository: PostCommentsRepository) =
        GetPostCommentsUseCase(repository)
}