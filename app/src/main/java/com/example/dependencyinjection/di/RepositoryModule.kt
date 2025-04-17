package com.example.dependencyinjection.di

import com.example.dependencyinjection.data.remote.ApiService
import com.example.dependencyinjection.data.repository.EventRepository
import com.example.dependencyinjection.data.repository.EventRepositoryImpl
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