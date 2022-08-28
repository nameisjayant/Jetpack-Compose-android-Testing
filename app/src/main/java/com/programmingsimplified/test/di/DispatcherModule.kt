package com.programmingsimplified.testinginandroid.di

import com.programmingsimplified.testinginandroid.utils.dispatcher.DefaultDispatcher
import com.programmingsimplified.testinginandroid.utils.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DispatcherModule {

    @Binds
    abstract fun providesDispatcher(
        dispatcher:DefaultDispatcher
    ):DispatcherProvider
}