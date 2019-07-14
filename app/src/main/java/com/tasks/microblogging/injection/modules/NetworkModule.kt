package com.tasks.microblogging.injection.modules

import com.tasks.microblogging.BuildConfig
import com.tasks.microblogging.data.remote.network.retrofit.MicroBloggingAPI
import com.tasks.microblogging.data.remote.network.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule(val baseURL: String = "") {

    object DAGGER_CONSTANTS {
        const val BASE_URL = "baseUrlString"
    }

    private object ApiEndpointsConstants {
        const val ProductionURL = "https://sym-json-server.herokuapp.com/"
        const val StagingURL = "https://sym-json-server.herokuapp.com/"
    }

    @Provides
    @Singleton
    @Named(value = DAGGER_CONSTANTS.BASE_URL)
    fun providesBaseUrl() =
        when {
            baseURL.isNotEmpty() -> baseURL
            BuildConfig.QA -> ApiEndpointsConstants.StagingURL
            else -> ApiEndpointsConstants.ProductionURL
        }

    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        @Named(DAGGER_CONSTANTS.BASE_URL) baseURL: String, httpClient: OkHttpClient,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        builder: Retrofit.Builder
    )
            : Retrofit = RetrofitClient(
        baseURL = baseURL,
        httpClient = httpClient.newBuilder(),
        httpLoggingInterceptor = httpLoggingInterceptor,
        builder = builder
    ).getInstance()

    @Provides
    @Singleton
    fun provideAccentureApi(retrofit: Retrofit)
            : MicroBloggingAPI = retrofit.create(MicroBloggingAPI::class.java)

}