package com.submission.dicodingstory.dependencyinjection

import android.content.Context
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.dbroom.AppDb
import com.submission.dicodingstory.repo.StoryMedRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [DependencyModule::class])
@InstallIn(SingletonComponent::class)
object PaginationModule {
    @Provides
    @Singleton
    fun providePostDao(db: AppDb) = db.storyDao()
    @Provides
    @Singleton
    fun provideMyDatabase(@ApplicationContext context: Context) =
        AppDb.getDB(context)


    @Provides
    @Singleton
    fun providePostMediatorRepository(db: AppDb, service: StoryApiService) =
        StoryMedRepo(db, service)
    @Provides
    @Singleton
    fun provideRemoteKeysDao(db: AppDb) = db.rKDao()

}