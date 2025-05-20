package com.example.dependencyinjection.Quest3.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dependencyinjection.Quest3.data.repository.StatsRepositoryImpl
import com.example.dependencyinjection.Quest3.domain.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideStatsRepository(): StatsRepository = StatsRepositoryImpl()
}
