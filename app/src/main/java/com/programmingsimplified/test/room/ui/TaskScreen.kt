package com.programmingsimplified.testinginandroid.room.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.programmingsimplified.testinginandroid.common.CommonProgress
import com.programmingsimplified.testinginandroid.room.Task
import com.programmingsimplified.testinginandroid.utils.Resource
import com.programmingsimplified.testinginandroid.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(
    isInput: Boolean,
    onChange: (Boolean) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isDialog by remember { mutableStateOf(false) }
    var isRefresh by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isUpdate by remember { mutableStateOf(false) }
    val res = viewModel.task.value

    if (isRefresh) {
        isRefresh = false
        viewModel.getTask()
    }


    if (isDialog)
        CommonProgress()

    if (isUpdate) {
        UpdateTask(
            task = viewModel.updateTask.value,
            onUpdate = { isUpdate = it },
            onRefresh = { isRefresh = it },
            viewModel = viewModel
        )
    }

    if(res.data.isNotEmpty())
        LazyColumn {
            items(
                res.data,
                key = {
                    it.id!!
                }
            ) { task ->
                TaskEachRow(task, onDelete = {
                    scope.launch(Dispatchers.Main){
                        viewModel.delete(task)
                            .collect{
                                when(it){
                                    is Resource.Success->{
                                        context.showMsg(it.data)
                                        isRefresh = true
                                    }
                                    is Resource.Failure->{
                                        context.showMsg(it.msg)
                                    }
                                    Resource.Loading-> {}
                                    Resource.Empty->{}
                                }
                            }
                    }
                }) {
                    viewModel.setData(task)
                    isUpdate = true
                }
            }
        }

    if(res.error.isNotEmpty())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error, color = Color.Gray)
        }
    if(res.isLoading)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }


    if (isInput) {
        AlertDialog(onDismissRequest = { onChange(false) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {

                    TextField(
                        value = title, onValueChange = {
                            title = it
                        },
                        label = { Text(text = "Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = description, onValueChange = {
                            description = it
                        },
                        label = { Text(text = "Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            buttons = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = {

                       scope.launch(Dispatchers.Main){
                           viewModel.insert(
                               Task(title = title, description = description)
                           ).collect{
                               when(it){
                                   is Resource.Success -> {
                                       isRefresh = true
                                       onChange(false)
                                       isDialog = false
                                       context.showMsg(it.data)
                                   }
                                   is Resource.Failure -> {
                                       context.showMsg(it.msg)
                                       onChange(false)
                                       isDialog = false
                                   }
                                   Resource.Loading ->{
                                       isDialog = true
                                   }
                                   Resource.Empty ->{}
                               }
                           }
                       }

                    }) {
                        Text(text = "Save")
                    }
                }
            }
        )
    }

}

@Composable
fun TaskEachRow(task: Task, onDelete: () -> Unit = {}, onUpdate: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onUpdate()
                }
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = task.title, style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ), color = Color.Black, modifier = Modifier.align(
                            CenterVertically
                        )
                    )
                    IconButton(onClick = {
                        onDelete()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = task.description, style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ), color = Color.Gray
                )
            }
        }
    }

}

@Composable
fun UpdateTask(
    task: Task,
    onUpdate: (Boolean) -> Unit,
    onRefresh: (Boolean) -> Unit,
    viewModel: TaskViewModel,
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    AlertDialog(onDismissRequest = { onUpdate(false) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                TextField(
                    value = title, onValueChange = {
                        title = it
                    },
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = description, onValueChange = {
                        description = it
                    },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        buttons = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                   scope.launch(Dispatchers.Main){
                       viewModel.update(
                           Task(title = title, description = description)
                       ).collect{
                           when(it){
                               is Resource.Success->{
                                   onRefresh(true)
                                   onUpdate(false)
                                   context.showMsg(it.data)
                               }
                               is Resource.Failure->{
                                   onUpdate(false)
                                   context.showMsg(it.msg)
                               }
                               Resource.Loading->{}
                               Resource.Empty->{}
                           }
                       }

                   }
                }) {
                    Text(text = "Update")
                }
            }
        }
    )


}