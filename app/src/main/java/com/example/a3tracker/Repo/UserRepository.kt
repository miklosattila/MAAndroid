package com.example.a3tracker.Repo

import android.text.style.UpdateLayout
import android.util.Log
import com.example.a3tracker.DataClasses.GetCUResponse
import com.example.a3tracker.DataClasses.LoginRequest
import com.example.a3tracker.DataClasses.LoginResponse
import com.example.a3tracker.DataClasses.UpdateProfileRequest
import com.example.a3tracker.Interfaces.ApiInterface
import retrofit2.Response

data class User(
    val id: Int,
    var name: String,
    val password: String,
    val email: String,
    val type: Int,
    var location: String,
    var phoneNumber: String,
    val departmentId: Int,
    var image: String,
    val token: String
)

val userList = CreateUser()

fun CreateUser(): MutableList<User> {
    val userList = mutableListOf<User>()
    for (index in 1..30) {
        val user = User(
            id = index + 1,
            name = "User ${index + 1}",
            password = "password${index + 1}",
            email = "user${index + 1}@example.com",
            type = if (index % 2 == 0) 1 else 2,
            location = "Location ${index + 1}",
            phoneNumber = "123456789",
            departmentId = (index % 3) + 1,
            image = "image${index + 1}.jpg",
            token = "live${index + 1}"
        )
        userList.add(user)
    }
    return userList
}


class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse? {

        var user = userList.find { it.email == loginRequest.email && it.password == loginRequest.password }
        if(user ==null)
            return null;
        else
            return LoginResponse(Long.MAX_VALUE, user.token,user.id)

    }

    suspend fun getCurrentUser(cuRequest: String): GetCUResponse? {
        var user = userList.find { it.token == cuRequest }
        Log.i("Token",cuRequest)
        if (user != null) {
            return GetCUResponse(user.id,user.name.split(" ")[1],user.name.split(" ")[0],user.email,user.type,user.location,user.phoneNumber,user.departmentId,user.image)
        }
        return null;
    }

    suspend fun getUsers(usersRequest: String): List<GetCUResponse>{
        var userCheck = userList.find { it.token == usersRequest }
        var listGetCUResponse :MutableList<GetCUResponse> =  mutableListOf()
        if(userCheck != null) {
            userList.forEach{user ->  listGetCUResponse.add(GetCUResponse(user.id,user.name.split(" ")[1],user.name.split(" ")[0],user.email,user.type,user.location,user.phoneNumber,user.departmentId,user.image))}
        }
        
        return listGetCUResponse
    }

    suspend fun updateProfile(token:String,updateProfileRequest: UpdateProfileRequest): String?{
        var userCheck = userList.find { it.token == token }
        Log.i("Updateuser",userCheck.toString())
        if(userCheck != null) {
            userCheck.name=updateProfileRequest.firstName+" "+updateProfileRequest.lastName
            userCheck.location=updateProfileRequest.location
            userCheck.phoneNumber=updateProfileRequest.phoneNumber
            userCheck.image=updateProfileRequest.imageUrl

            return "Updated ${userList.find { it.token == token }}"
        }
        return "Error user not exist"
    }

}