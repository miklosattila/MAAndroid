package com.example.a3tracker.DataClasses

import com.google.gson.annotations.SerializedName

data class GetDepartmentsResponse(
    @SerializedName("ID")
    val departmentId : Int,
    @SerializedName("name")
    val departmentName: String
)
