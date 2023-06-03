package com.example.a3tracker.Repo

import com.example.a3tracker.DataClasses.GetCUResponse
import com.example.a3tracker.DataClasses.GetDepartmentsResponse
import com.example.a3tracker.Interfaces.ApiInterface
import retrofit2.Response
val DepartmentsList: List<GetDepartmentsResponse> = listOf(
    GetDepartmentsResponse(1, "Department 1"),
    GetDepartmentsResponse(2, "Department 2")
)

class DepartmentsRepository {
    suspend fun getDepartments(token: String): List<GetDepartmentsResponse>? {
        var userCheck = userList.find { it.token == token }
        if(userCheck != null) {
            return DepartmentsList
        }

      return null;
    }
}