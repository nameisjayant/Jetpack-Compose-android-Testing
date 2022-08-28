package com.programmingsimplified.testinginandroid.room.db

import androidx.room.*
import com.programmingsimplified.testinginandroid.room.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun get(id:Int):Task?

    @Update
    suspend fun update(task:Task)
}