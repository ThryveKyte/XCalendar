package com.debanshu.xcalendar.domain.states

import androidx.compose.runtime.Immutable
import com.debanshu.xcalendar.domain.model.Task

@Immutable
data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
