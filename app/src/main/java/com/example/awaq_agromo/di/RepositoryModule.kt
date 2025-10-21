package com.example.awaq_agromo.di

import android.content.Context
import com.example.awaq_agromo.data.local.DataStoreManager
import com.example.awaq_agromo.data.repository.UserRepositoryImpl
import com.example.awaq_agromo.data.remote.api.UserApi
import com.example.awaq_agromo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}