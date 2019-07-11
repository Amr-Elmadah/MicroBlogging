package com.tasks.microblogging.ui.authorslist.data.local

import androidx.lifecycle.LiveData
import com.tasks.microblogging.data.local.dao.AuthorDao
import com.tasks.microblogging.data.local.entity.AuthorEntity
import io.reactivex.Single
import javax.inject.Inject

class AuthorsLocalDataSource @Inject constructor(private val authorDao: AuthorDao) {

    fun insertAuthors(authors: List<AuthorEntity>): Single<Boolean> =
        Single.create {
            authorDao.insertAuthors(authors)
            it.onSuccess(true)
        }

    fun getCachedAuthors(): LiveData<List<AuthorEntity>> =
        authorDao.getAllAuthors()
}