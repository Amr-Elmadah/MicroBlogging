package com.tasks.microblogging.ui.authorslist.domain.interactor

import com.tasks.microblogging.base.domain.interactor.ListUseCase
import com.tasks.microblogging.data.remote.network.response.Author
import com.tasks.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAuthorsUseCase @Inject constructor(private val repository: AuthorsRepository) :
    ListUseCase<String, Author>() {
    override fun build(params: String): Single<List<Author>> =
        repository.getAuthors()
}