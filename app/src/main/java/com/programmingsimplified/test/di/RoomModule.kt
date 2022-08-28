package com.programmingsimplified.testinginandroid.di

import android.app.Application
import androidx.room.Room
import com.programmingsimplified.testinginandroid.room.db.TaskDao
import com.programmingsimplified.testinginandroid.room.db.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesDatabase(context:Application):TaskDatabase =
        Room.databaseBuilder(context,TaskDatabase::class.java,"task_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesTaskDao(db:TaskDatabase):TaskDao = db.dao

}