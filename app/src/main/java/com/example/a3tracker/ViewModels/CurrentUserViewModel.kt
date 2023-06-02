package com.example.a3tracker.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.DataClasses.GetCUResponse
import com.example.a3tracker.DataClasses.LoginResponse
import com.example.a3tracker.Repo.UserRepository
import kotlinx.coroutines.launch

data class User(
    var ID: Int = 0,
    var department_id: Int = 0,
    var email: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var location: String? = null,
    var phone_number: String? = null,
    var type: Int = 0,
    var loginResponse: LoginResponse = LoginResponse(123456,"",420),
    var imageUrl : String = "",
    var mentor : GetCUResponse? = null
) : java.io.Serializable

class CurrentUserViewModel : ViewModel(){
    private val _uiState = MutableLiveData(User())

    fun getID():Int{
        return _uiState.value?.ID ?: 0
    }

    fun getDepartmentId() : Int{
        return _uiState.value?.department_id ?: 0
    }

    fun getName(): String{
        return "${_uiState.value?.first_name} ${_uiState.value?.last_name}"
    }

    fun getFirstName(): String{
        return _uiState.value?.first_name.toString()
    }

    fun getLastName(): String{
        return _uiState.value?.last_name.toString()
    }

    fun getEmail():String{
        return _uiState.value?.email ?: ""
    }

    fun getType():Int{
        return _uiState.value?.type ?: 0
    }

    fun getLocation():String?{
        return _uiState.value?.location
    }

    fun getPhoneNumber():String?{
        return _uiState.value?.phone_number
    }

    fun getImageUrl():String{
        return _uiState.value?.imageUrl ?: ""
    }

    fun getToken():String{
        return _uiState.value?.loginResponse?.token ?: ""
    }

    fun getDeadline():Long{
        return _uiState.value?.loginResponse?.deadline ?: 0
    }

    fun getMentor(): GetCUResponse? {
        return _uiState.value?.mentor
    }

    fun updateMentor(newMentor : GetCUResponse){
        _uiState.value?.mentor = newMentor
    }

    fun updateLoginResponse(deadline: Long,token: String,userId : Int ){
        _uiState.value?.loginResponse= LoginResponse(deadline,token,userId)
    }

    fun updateUserId(newId : Int){
        _uiState.value?.ID = newId
    }

    fun updateDepartmentId(newDepartment : Int){
        _uiState.value?.department_id  = newDepartment
    }

    fun updateEmail( newEmail : String){
        _uiState.value?.email = newEmail
    }

    fun updateName(newFN : String, newLN : String){
        _uiState.value?.first_name = newFN
        _uiState.value?.last_name = newLN
    }

    fun updateLocation(newLocation : String){
        _uiState.value?.location = newLocation
    }

    fun updatePhoneNumber(newPhoneNumber : String){
        _uiState.value?.phone_number = newPhoneNumber
    }

    fun updateType(newType : Int){
        _uiState.value?.type = newType
    }

    fun updateImage(newImage : String){
        _uiState.value?.imageUrl = newImage
    }

    fun getCurrentUser():Boolean{
        val token = _uiState.value?.loginResponse!!.token
        val userRepo = UserRepository()
        var withoutException = true
        viewModelScope.launch {
            try {
                val response = userRepo.getCurrentUser(cuRequest = token)
                if(response.isSuccessful){
                    val responses = response.body().toString().trim().split(",")
                    val responsesToUse : MutableList<String> = mutableListOf()
                    for(r in responses){
                        val temp = r.split("=")[1]
                        responsesToUse.add(temp)
                    }
                    for(r in responsesToUse){
                        Log.i("CurrentUser", r)
                    }
                    updateUserId(responsesToUse[0].toInt())
                    updateName(responsesToUse[2],responsesToUse[1])
                    updateEmail(responsesToUse[3])
                    updateType(responsesToUse[4].toInt())
                    updateLocation(responsesToUse[5])
                    updatePhoneNumber(responsesToUse[6])
                    updateDepartmentId(responsesToUse[7].toInt())
                    updateImage(responsesToUse[8].dropLast(1))
                    updateLoginResponse(getDeadline(),getToken(),responsesToUse[0].toInt())
                    Log.i("CurrentUser",getName())
                }
            } catch (ex: Exception){
                Log.i("CU VM Exception",ex.message,ex)
                withoutException = false
            }
        }
        return withoutException
    }

}

