package com.tasks.microblogging.ui.authorslist.domain.interactor

import androidx.lifecycle.LiveData
import com.tasks.microblogging.data.local.entity.AuthorEntity
import com.tasks.microblogging.ui.authorslist.domain.repository.AuthorsRepository
import javax.inject.Inject

class GetCachedAuthorsUseCase @Inject constructor(private val repository: AuthorsRepository) {
    fun build(): LiveData<List<AuthorEntity>> =
        repository.getCachedAuthors()
}