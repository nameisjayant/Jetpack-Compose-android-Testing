package com.programmingsimplified.test.room.ui

import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.room.repository.TaskRepository
import com.programmingsimplified.testinginandroid.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository : TaskRepository {

    private val fakeFlow = MutableSharedFlow<Resource<List<Task>>>()
    suspend fun emit(value:Resource<List<Task>>) = fakeFlow.emit(value)

    override suspend fun insert(task: Task): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
    }

    override suspend fun update(task: Task): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
    }

    override suspend fun delete(task: Task): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
    }

    override fun getAllTask(): Flow<Resource<List<Task>>> = fakeFlow
}