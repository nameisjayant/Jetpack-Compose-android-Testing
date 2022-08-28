package com.programmingsimplified.testinginandroid.di

import com.programmingsimplified.testinginandroid.room.repository.TaskRepository
import com.programmingsimplified.testinginandroid.room.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesTaskRepository(
        repo:TaskRepositoryImpl
    ):TaskRepository
}