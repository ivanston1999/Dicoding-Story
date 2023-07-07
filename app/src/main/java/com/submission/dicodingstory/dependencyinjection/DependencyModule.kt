package com.submission.dicodingstory.dependencyinjection

import android.content.Context
import com.submission.dicodingstory.BuildConfig.BASE_URL
import com.submission.dicodingstory.BuildConfig.DEBUG
import com.submission.dicodingstory.api.AccountApiService
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.api.TokenInterceptor
import com.submission.dicodingstory.data.StoryData
import com.submission.dicodingstory.error.NetworkHandler
import com.submission.dicodingstory.error.ResponseHandler
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.util.AccountPref
import com.submission.dicodingstory.util.LoadingAnimation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {

    @Provides
    @Singleton
    fun provideLoadingAnimation() =
        LoadingAnimation()



    @Provides
    @Singleton
    fun provideAppRepo(data: StoryData) =
        AppRepo(data)
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) =
        AccountPref(context)
    @Provides
    @Singleton
    fun provideNetworkHandler() =
        NetworkHandler()




    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        prefs: AccountPref
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor(prefs))
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): AccountApiService =
        retrofit.create(AccountApiService::class.java)

    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit): StoryApiService =
        retrofit.create(StoryApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpInterceptor() = HttpLoggingInterceptor().apply{
        level = if
                        (DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE}
    @Provides
    @Singleton
    fun provideErrorParser(retrofit: Retrofit) = ResponseHandler(retrofit)

    @Provides
    @Singleton
    fun provideStoryData
                (
        networkHandler: NetworkHandler,
        converter: ResponseHandler,
        accountApiService: AccountApiService,
        storyApiService: StoryApiService
    ) =
        StoryData(networkHandler, converter, accountApiService, storyApiService)


}