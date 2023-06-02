package com.example.a3tracker.DataClasses

import com.example.a3tracker.Enums.TaskPriority
import com.example.a3tracker.Enums.TaskStatus
import java.util.Date

data class Tasks(
    val taskId : Int,
    val title : String,
    val groupId : Int,
    val createdAt : Date,
    val createdBy: Int,
    val assignee : Int,
    val deadline: Date,
    val status : TaskStatus,
    val priority: TaskPriority,
    val description: String,
    val progress: Int
)
