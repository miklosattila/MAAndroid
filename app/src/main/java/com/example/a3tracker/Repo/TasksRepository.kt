package com.example.a3tracker.Repo

import com.example.a3tracker.DataClasses.CreateTaskRequest
import com.example.a3tracker.DataClasses.GetTasksResponse
import com.example.a3tracker.Interfaces.ApiInterface
import retrofit2.Response

class TasksRepository {
    suspend fun createTask(token:String,createTaskRequest: CreateTaskRequest): Response<String>? {
        return ApiInterface.getApi()?.createTask(token = token, createTaskRequest = createTaskRequest)
    }

    suspend fun getTasks(token:String): Response<List<GetTasksResponse>>?{
        return ApiInterface.getApi()?.getTasks(token = token)
    }
}