package com.example.a3tracker.DataClasses

import com.google.gson.annotations.SerializedName

data class GetTasksResponse (
    val ID :Int,
    val title : String,
    val description: String,
    @SerializedName("created_time")
    val createdTime: Long,
    @SerializedName("created_by_user_ID")
    val createdByUser: Int,
    @SerializedName("asigned_to_user_ID")
    val assignedToUser: Int,
    val priority: Int,
    val deadline: Long,
    @SerializedName("department_ID")
    val departmentId: Int,
    val status:Int,
    val progress: Int
)