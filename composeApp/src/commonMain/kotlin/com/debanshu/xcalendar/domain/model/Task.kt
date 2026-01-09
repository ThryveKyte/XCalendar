package com.debanshu.xcalendar.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class Task(
    val id: String,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val category: String? = null,
    val createdAt: Long = 0L
)

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}
