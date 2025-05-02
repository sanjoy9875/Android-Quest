package com.example.dependencyinjection.Quest1.di

import com.example.dependencyinjection.Quest1.data.remote.ApiService
import com.example.dependencyinjection.Quest1.data.repository.EventRepository
import com.example.dependencyinjection.Quest1.data.repository.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideEventRepository(apiService: ApiService): EventRepository =
        EventRepositoryImpl(apiService)
}