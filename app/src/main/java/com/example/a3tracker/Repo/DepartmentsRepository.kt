package com.example.a3tracker.Repo

import com.example.a3tracker.DataClasses.GetDepartmentsResponse
import com.example.a3tracker.Interfaces.ApiInterface
import retrofit2.Response

class DepartmentsRepository {
    suspend fun getDepartments(token:String) : Response<List<GetDepartmentsResponse>>? {
        return ApiInterface.getApi()?.getDepartments(token = token)
    }
}