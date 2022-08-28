package com.programmingsimplified.test.room.ui

import com.google.common.truth.Truth.assertThat
import com.programmingsimplified.test.utils.FakeDispatcherTest
import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.room.ui.TaskViewModel
import com.programmingsimplified.testinginandroid.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class TaskViewModelTest{

    private lateinit var viewModel:TaskViewModel
    private val dispatcher = FakeDispatcherTest()
    private val repository = FakeTaskRepository()

    private val taskList = listOf(
        Task(1,"Hey","How are you"),
        Task(2,"Hey","I am fine")
    )

    @Before
    fun setUp(){
        viewModel = TaskViewModel(repository,dispatcher)
    }


    @Test
    fun `for success response, data must not be null`() = runTest(UnconfinedTestDispatcher()){
        repository.emit(Resource.Success(taskList))
        val res = viewModel.task.value
        assertThat(res.data).isNotEmpty()
        assertThat(res.data).hasSize(2)
        assertThat(res.error).isEmpty()
        assertThat(res.isLoading).isFalse()
    }

    @Test
    fun  `for loading response, data must be null`() = runTest(UnconfinedTestDispatcher()){
        repository.emit(Resource.Loading)
        val res = viewModel.task.value
        assertThat(res.data).isEmpty()
        assertThat(res.data).hasSize(0)
        assertThat(res.error).isEmpty()
        assertThat(res.isLoading).isTrue()
    }



    @Test
    fun  ` for error response, data must be null`() = runTest(UnconfinedTestDispatcher()){
        repository.emit(Resource.Failure("error"))
        val res = viewModel.task.value
        assertThat(res.data).isEmpty()
        assertThat(res.data).hasSize(0)
        assertThat(res.error).isNotEmpty()
        assertThat(res.isLoading).isFalse()
    }

}