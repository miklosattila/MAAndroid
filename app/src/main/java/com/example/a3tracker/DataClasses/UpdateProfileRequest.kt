package com.example.a3tracker.DataClasses

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("lastName")
    val lastName : String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("location")
    val location:String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
