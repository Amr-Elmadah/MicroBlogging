package com.tasks.microblogging.ui.authorslist.domain.repository

import androidx.lifecycle.LiveData
import com.tasks.microblogging.data.local.entity.AuthorEntity
import com.tasks.microblogging.data.remote.network.response.Author
import io.reactivex.Single

interface AuthorsRepository {
    fun getAuthors(): Single<List<Author>>

    fun insertAuthors(authors: List<AuthorEntity>): Single<Boolean>

    fun getCachedAuthors(): LiveData<List<AuthorEntity>>
}