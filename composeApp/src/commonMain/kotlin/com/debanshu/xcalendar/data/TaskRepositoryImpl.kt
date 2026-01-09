package com.debanshu.xcalendar.data

import com.debanshu.xcalendar.data.localDataSource.TaskDao
import com.debanshu.xcalendar.data.localDataSource.model.toDomain
import com.debanshu.xcalendar.data.localDataSource.model.toEntity
import com.debanshu.xcalendar.domain.model.Task
import com.debanshu.xcalendar.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTaskById(id: String): Task? {
        return taskDao.getTaskById(id)?.toDomain()
    }

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun toggleTaskCompletion(id: String, isCompleted: Boolean) {
        taskDao.updateTaskCompletionStatus(id, isCompleted)
    }
}
