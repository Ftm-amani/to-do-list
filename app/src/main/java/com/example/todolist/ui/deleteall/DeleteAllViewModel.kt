package com.example.todolist.ui.deleteall

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.todolist.data.TaskDao
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllViewModel @ViewModelInject constructor(
        val taskDao: TaskDao,
        @ApplicationScope private var applicationScope: CoroutineScope
    ): ViewModel() {

        fun onConfirmClick() = applicationScope.launch {
            taskDao.deleteAllTasks()
        }
}