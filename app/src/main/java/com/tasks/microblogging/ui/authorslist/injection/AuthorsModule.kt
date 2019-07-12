package com.tasks.microblogging.ui.authorslist.injection

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasks.microblogging.data.local.dao.AuthorDao
import com.tasks.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import com.tasks.microblogging.ui.authorslist.data.local.AuthorsLocalDataSource
import com.tasks.microblogging.ui.authorslist.data.remote.AuthorsRemoteDataSource
import com.tasks.microblogging.ui.authorslist.domain.interactor.GetAuthorsUseCase
import com.tasks.microblogging.ui.authorslist.domain.interactor.AddAuthorsLocalUseCase
import com.tasks.microblogging.ui.authorslist.domain.interactor.GetCachedAuthorsUseCase
import com.tasks.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import com.tasks.microblogging.ui.authorslist.domain.repository.AuthorsRepositoryImp
import com.tasks.microblogging.ui.authorslist.presetation.view.adapter.AuthorsAdapter
import com.tasks.microblogging.ui.authorslist.presetation.viewmodel.AuthorsViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthorsModule {

    @Provides
    fun provideAuthorsLocalDataSource(authorDao: AuthorDao) =
        AuthorsLocalDataSource(authorDao = authorDao)

    @Provides
    fun provideAuthorsRemoteDataSource(microBloggingAPI: MicroBloggingAPI) =
        AuthorsRemoteDataSource(microBloggingAPI = microBloggingAPI)

    @Provides
    fun provideAuthorsRepository(
        remoteDataSource: AuthorsRemoteDataSource,
        localDataSource: AuthorsLocalDataSource
    ): AuthorsRepository =
        AuthorsRepositoryImp(remoteDataSource, localDataSource)

    @Provides
    fun provideGetAuthorsUseCase(repository: AuthorsRepository) =
        GetAuthorsUseCase(repository)

    @Provides
    fun provideAddAuthorsLocalUseCase(repository: AuthorsRepository) =
        AddAuthorsLocalUseCase(repository)

    @Provides
    fun provideAuthorsViewModel(
        getAuthorsUseCase: GetAuthorsUseCase,
        addAuthorsLocalUseCase: AddAuthorsLocalUseCase
        , getCachedAuthorsUseCase: GetCachedAuthorsUseCase
    ) =
        AuthorsViewModel(getAuthorsUseCase, addAuthorsLocalUseCase, getCachedAuthorsUseCase)

    @Provides
    fun provideLinearLayoutManager(context: Context) =
        LinearLayoutManager(context)

    @Provides
    fun provideAuthorsAdapter() =
        AuthorsAdapter()
}