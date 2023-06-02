package com.example.a3tracker.DataClasses

import com.google.gson.annotations.SerializedName

data class CreateTaskRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("assignedToUserId")
    val userId: Int,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("deadline")
    val deadline: Long,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("status")
    val status:Int

)
