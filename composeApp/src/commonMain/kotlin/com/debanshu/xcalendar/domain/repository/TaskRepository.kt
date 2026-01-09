package com.debanshu.xcalendar.domain.repository

import com.debanshu.xcalendar.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun toggleTaskCompletion(id: String, isCompleted: Boolean)
}
