package com.example.a3tracker.Repo

import com.example.a3tracker.DataClasses.CreateTaskRequest
import com.example.a3tracker.DataClasses.GetTasksResponse

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val createdTime: Long,
    val createdByUser: Int,
    val assignedToUser: Int,
    val priority: Int,
    val deadline: Long,
    val departmentId: Int,
    val progress: Int,
    val status: Int
)
fun Createtask(): MutableList<Task> {
    val tasksList = mutableListOf<Task>()
    for (i in 1..100) {
        val task = Task(
            i,
            "Task $i",
            "Description $i",
            System.currentTimeMillis(),
            i % 5 + 1,
            i % 5 + 1,
            i % 2,
            System.currentTimeMillis(),
            i % 4 + 1,
            i%9+1 * 10,
            i % 4
        )
        tasksList.add(task)
    }
    return tasksList
}

val tasksList = Createtask()

class TasksRepository {

    suspend fun createTask(token:String,createTaskRequest: CreateTaskRequest): String? {

        var user = userList.find { it.token == token }
        tasksList.add(Task(userList.size,createTaskRequest.title,createTaskRequest.description,System.currentTimeMillis(),createTaskRequest.userId,
            0,createTaskRequest.priority,createTaskRequest.deadline,createTaskRequest.departmentId,0,createTaskRequest.status))
        return "Your token expired"
    }

    suspend fun getTasks(token:String): List<GetTasksResponse>?{
        var user = userList.find { it.token == token }
        var tasks = tasksList.filter{it.createdByUser == user?.id}.map { GetTasksResponse(it.id,it.title,it.description,it.createdTime,
        it.createdByUser,it.assignedToUser,it.priority,it.deadline,it.departmentId,it.status,it.progress) }

        return tasks
    }
}