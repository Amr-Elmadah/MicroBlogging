package com.tasks.microblogging.ui.authorslist.domain.interactor

import com.tasks.microblogging.data.local.entity.AuthorEntity
import com.tasks.microblogging.base.domain.interactor.SingleUseCase
import com.tasks.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import io.reactivex.Single
import javax.inject.Inject

class AddAuthorsLocalUseCase @Inject constructor(private val repository: AuthorsRepository) :
    SingleUseCase<List<AuthorEntity>, Boolean>() {
    override fun build(params: List<AuthorEntity>): Single<Boolean> =
        repository.insertAuthors(authors = params)
}