package com.example.awaq_agromo.data.local.database

import android.content.Context
import androidx.room.Room
import com.example.awaq_agromo.data.local.daos.HumedadDao
import com.example.awaq_agromo.data.local.db.VariedadDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import jakarta.inject.Singleton
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "awaq_agromo.db"
        ).build()
    }

    @Provides
    fun provideVariedadDao(database: AppDatabase): VariedadDao {
        return database.variedadDao()
    }

    @Provides
    fun provideHumedadDao(database: AppDatabase): HumedadDao {
        return database.humedadDao()
    }
}
