package com.debanshu.xcalendar.ui.screen.taskScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.debanshu.xcalendar.domain.model.Task
import com.debanshu.xcalendar.domain.model.TaskPriority
import com.debanshu.xcalendar.ui.TaskViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddTaskDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Tasks") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddTaskDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onCheckedChange = { viewModel.toggleTaskCompletion(task) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }

        if (showAddTaskDialog) {
            AddTaskDialog(
                onDismiss = { showAddTaskDialog = false },
                onConfirm = { title, description ->
                    viewModel.addTask(
                        Task(
                            id = Random.nextLong().toString(),
                            title = title,
                            description = description,
                            createdAt = Clock.System.now().toEpochMilliseconds()
                        )
                    )
                    showAddTaskDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange
            )
            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (task.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                )
                if (!task.description.isNullOrEmpty()) {
                    Text(text = task.description, style = MaterialTheme.typography.bodySmall)
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task")
            }
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            }
        },
        confirmButton = {
            Button(onClick = { if (title.isNotBlank()) onConfirm(title, description) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Add Clock import if not available
