package com.tasks.microblogging.data.remote.network.retrofit

import com.tasks.microblogging.data.remote.network.response.Author
import io.reactivex.Single
import retrofit2.http.GET

interface MicroBloggingAPI {
    @GET("authors")
    fun loadAuthors(): Single<List<Author>>
}