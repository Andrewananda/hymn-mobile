package com.devstart.hymn.di.module

import android.content.Context
import androidx.room.Room
import com.devstart.hymn.data.dao.CategoryDao
import com.devstart.hymn.data.dao.SongDao
import com.devstart.hymn.data.db.HymnDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideSongDao(appDatabase: HymnDatabase) : SongDao {
        return appDatabase.songDao()
    }

    @Provides
    @Singleton
    fun provideCategory(appDatabase: HymnDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : HymnDatabase {
        return Room.databaseBuilder(
            context,
            HymnDatabase::class.java,
            "hymn"
        ).fallbackToDestructiveMigration().build()
    }
}