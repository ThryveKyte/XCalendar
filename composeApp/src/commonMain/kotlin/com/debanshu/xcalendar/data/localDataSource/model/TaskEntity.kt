package com.debanshu.xcalendar.data.localDataSource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.debanshu.xcalendar.domain.model.Task
import com.debanshu.xcalendar.domain.model.TaskPriority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val dueDate: Long?,
    val isCompleted: Boolean,
    val priority: String,
    val category: String?,
    val createdAt: Long
)

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    dueDate = dueDate,
    isCompleted = isCompleted,
    priority = TaskPriority.valueOf(priority),
    category = category,
    createdAt = createdAt
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    dueDate = dueDate,
    isCompleted = isCompleted,
    priority = priority.name,
    category = category,
    createdAt = createdAt
)
