package com.programmingsimplified.testinginandroid.room.repository

import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.room.db.TaskDao
import com.programmingsimplified.testinginandroid.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {


    override suspend fun insert(task: Task):Flow<Resource<String>> = flow{
        emit(Resource.Loading)
        try {
            dao.insert(task)
            emit(Resource.Success("Data added Successfully"))
        }catch (e:Exception){
            emit(Resource.Failure(e.message!!))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun update(task: Task):Flow<Resource<String>> = flow{
        emit(Resource.Loading)
        try {
            dao.update(task)
            emit(Resource.Success("Update Successfully"))
        }catch (e:Exception){
            emit(Resource.Failure(e.message!!))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun delete(task: Task):Flow<Resource<String>> =  flow{
        emit(Resource.Loading)
        try {
            dao.delete(task)
            emit(Resource.Success("Data deleted successfully.."))
        }catch (e:Exception){
            emit(Resource.Failure(e.message!!))
        }

    }.flowOn(Dispatchers.IO)

    override fun getAllTask(): Flow<Resource<List<Task>>> = flow{
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dao.getAllTask().first()))
        }catch (e:Exception){
            emit(Resource.Failure(e.message!!))
        }
    }
}