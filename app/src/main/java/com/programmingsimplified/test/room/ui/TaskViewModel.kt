package com.programmingsimplified.testinginandroid.room.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.room.repository.TaskRepository
import com.programmingsimplified.testinginandroid.utils.Resource
import com.programmingsimplified.testinginandroid.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repo: TaskRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _task: MutableState<TaskState> = mutableStateOf(TaskState())
    val task: State<TaskState> = _task

    private val _updateTask: MutableState<Task> = mutableStateOf(Task())
    val updateTask: State<Task> = _updateTask


    fun setData(data: Task) {
        _updateTask.value = data
    }

    suspend fun insert(task: Task) = repo.insert(task)


    suspend fun delete(task: Task) = repo.delete(task)


    suspend fun update(task: Task) = repo.update(task)


    init {
        getTask()
    }

    fun getTask() = viewModelScope.launch(dispatcher.main) {
        repo.getAllTask()
            .collect{
                when(it){
                    is Resource.Success->{
                        _task.value = TaskState(
                            data = it.data
                        )
                    }
                    is Resource.Failure->{
                        _task.value = TaskState(
                            error = it.msg
                        )
                    }
                    Resource.Loading->{
                        _task.value = TaskState(
                            isLoading = true
                        )
                    }
                    Resource.Empty->{}
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        this.onCleared()
    }

}

data class TaskState(
    val data:List<Task> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)