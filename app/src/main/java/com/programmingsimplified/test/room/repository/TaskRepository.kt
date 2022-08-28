package com.programmingsimplified.testinginandroid.room.repository

import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository  {

    suspend fun insert(task:Task):Flow<Resource<String>>
    suspend fun update(task: Task):Flow<Resource<String>>
    suspend fun delete(task: Task):Flow<Resource<String>>
    fun getAllTask():Flow<Resource<List<Task>>>
}