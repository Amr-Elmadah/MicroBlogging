package com.tasks.microblogging.injection.context

import com.tasks.microblogging.ui.authordetails.injection.AuthorDetailsModule
import com.tasks.microblogging.ui.authordetails.presentation.view.activity.AuthorDetailsActivity
import com.tasks.microblogging.ui.authorslist.injection.AuthorsModule
import com.tasks.microblogging.ui.authorslist.presetation.view.activity.AuthorsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(AuthorsModule::class)])
    abstract fun bindAuthorsActivity(): AuthorsActivity

    @ContributesAndroidInjector(modules = [(AuthorDetailsModule::class)])
    abstract fun bindAuthorDetailsActivity(): AuthorDetailsActivity
}

