package com.programmingsimplified.testinginandroid.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.programmingsimplified.testinginandroid.room.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract val dao:TaskDao

}