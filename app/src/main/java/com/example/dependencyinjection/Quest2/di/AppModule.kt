package com.example.dependencyinjection.Quest2.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.dependencyinjection.Quest2.data.api.ProductApiService
import com.example.dependencyinjection.Quest2.data.db.ProductDao
import com.example.dependencyinjection.Quest2.data.db.ProductDatabase
import com.example.dependencyinjection.Quest2.data.repository.ProductRepositoryImpl
import com.example.dependencyinjection.Quest2.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ProductApiService =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)

    @Provides
    @Singleton
    fun provideDb(app: Application): ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase::class.java, "products.db").build()

    @Provides
    fun provideDao(db: ProductDatabase): ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideRepository(api: ProductApiService, dao: ProductDao): ProductRepository =
        ProductRepositoryImpl(api, dao)
}
