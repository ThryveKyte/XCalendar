package com.debanshu.xcalendar.data.localDataSource

import androidx.room.*
import com.debanshu.xcalendar.data.localDataSource.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTaskCompletionStatus(id: String, isCompleted: Boolean)
}
