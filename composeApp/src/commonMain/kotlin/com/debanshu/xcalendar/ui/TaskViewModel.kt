package com.debanshu.xcalendar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshu.xcalendar.domain.model.Task
import com.debanshu.xcalendar.domain.repository.TaskRepository
import com.debanshu.xcalendar.domain.states.TaskUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState(isLoading = true))
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks()
                .catch { exception ->
                    _uiState.update { it.copy(isLoading = false, error = exception.message) }
                }
                .collect { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false) }
                }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.addTask(task)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.toggleTaskCompletion(task.id, !task.isCompleted)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
