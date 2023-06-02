package com.example.a3tracker.Interfaces

import com.example.a3tracker.DataClasses.*
import com.example.a3tracker.Objects.ApiClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface{


    @GET("/user")
    suspend fun getCurrentUser(@Header("token") token: String): Response<GetCUResponse>

    @POST("/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/users")
    suspend fun getUsers(@Header("token") token: String): Response<List<GetCUResponse>>

    @POST("/task/create")
    suspend fun createTask(@Header("token") token: String,@Body createTaskRequest: CreateTaskRequest) : Response<String>

    @GET("/task/getTasks")
    suspend fun getTasks(@Header("token") token:String) : Response<List<GetTasksResponse>>

    @GET("/department")
    suspend fun getDepartments(@Header("token") token:String) : Response<List<GetDepartmentsResponse>>

    @POST("/users/updateProfile")
    suspend fun updateProfile(@Header("token") token:String,@Body updateProfileRequest: UpdateProfileRequest): Response<String>

    companion object {
        fun getApi(): ApiInterface? {
            return ApiClient.client?.create(ApiInterface::class.java)
        }
    }

}