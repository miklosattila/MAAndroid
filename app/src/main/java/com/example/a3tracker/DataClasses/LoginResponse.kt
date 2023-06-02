package com.example.a3tracker.DataClasses

data class LoginResponse(
    val deadline: Long,
    val token: String,
    val userId: Int
)