package com.programmingsimplified.testinginandroid.utils.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDispatcher @Inject constructor() : DispatcherProvider {

    override val main: CoroutineDispatcher get()  = Dispatchers.Main
    override val io: CoroutineDispatcher get()  = Dispatchers.IO
    override val default: CoroutineDispatcher get()  = Dispatchers.Default
}