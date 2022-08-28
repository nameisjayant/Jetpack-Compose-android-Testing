package com.programmingsimplified.test.room.db


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.room.db.TaskDao
import com.programmingsimplified.testinginandroid.room.db.TaskDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class TaskDatabaseTest{

    private lateinit var db:TaskDatabase
    private lateinit var dao:TaskDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.dao
    }

    @After
    fun tearUp(){
        db.close()
    }


    @Test
    fun save_task_into_table() = runTest(UnconfinedTestDispatcher()) {
        val task = Task(1,"Hey","How are you?")
        dao.insert(task)

        val tasks = dao.getAllTask().first()
        assertThat(tasks).contains(task)
        assertThat(tasks).hasSize(1)
    }

    @Test
    fun save_task_sameTaskSaveAgain_replaceWithNewOne() = runTest(UnconfinedTestDispatcher()){
        val task1 = Task(1,"Hey","How are you?")
        val task2 = Task(1,"Hey","How are you?")
        dao.insert(task1)
        dao.insert(task2)
        val tasks = dao.getAllTask().first()
        assertThat(tasks).contains(task2)
        assertThat(tasks).hasSize(1)
    }

    @Test
    fun update_task_in_the_table() = runTest(UnconfinedTestDispatcher()){
        val task = Task(1,"Hey","How are you?")
        dao.insert(task)
        val updateTask = task.copy(
            1,"Hello","I am fine"
        )
        dao.update(updateTask)
        assertThat(dao.get(task.id!!)).isEqualTo(updateTask)
    }

    @Test
    fun delete_task_from_table() = runTest(UnconfinedTestDispatcher()){
        val task1 = Task(1,"Hey","How are you?")
        val task2 = Task(2,"Hey1","How are you2")
        val task3 = Task(3,"Hey2","How are you3")
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)
        dao.delete(task2)
        val tasks = dao.getAllTask().first()
        assertThat(tasks).doesNotContain(task2)
        assertThat(tasks).containsExactly(task1,task3)
        assertThat(tasks).hasSize(2)
    }

    @Test
    fun check_table_is_empty_or_not() = runTest(UnconfinedTestDispatcher()){
        val task = Task(1,"Hey","How are you?")
        dao.insert(task)
        dao.delete(task)
        val tasks = dao.getAllTask().first()
        assertThat(tasks).hasSize(0)
    }

    @Test
    fun get_all_task_from_table() = runTest(UnconfinedTestDispatcher()){
        val task1 = Task(1,"Hey","How are you?")
        val task2 = Task(2,"Hey","How are you?")
        dao.insert(task1)
        dao.insert(task2)
        val tasks = dao.getAllTask().first()
        assertThat(tasks).hasSize(2)
    }
}