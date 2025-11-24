package com.macaneta.mahungu.di

import com.macaneta.mahungu.data.source.remote.repository.FeedRepository
import com.macaneta.mahungu.data.source.remote.repository.impl.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindsFeedRepository(impl: FeedRepositoryImpl): FeedRepository
}
