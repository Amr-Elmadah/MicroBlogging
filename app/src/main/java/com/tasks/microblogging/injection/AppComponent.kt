package com.tasks.microblogging.injection

import android.app.Application
import com.tasks.microblogging.App
import com.tasks.microblogging.injection.context.ActivityBuilder
import com.tasks.microblogging.injection.modules.AuthorsDatabaseModule
import com.tasks.microblogging.injection.modules.AppModule
import com.tasks.microblogging.injection.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        AuthorsDatabaseModule::class,
        NetworkModule::class]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}