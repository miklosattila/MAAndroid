package com.example.a3tracker.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.DataClasses.LoginRequest
import com.example.a3tracker.Enums.LoginResult
import com.example.a3tracker.Repo.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    companion object{
        val TAG: String = LoginViewModel::class.java.simpleName
    }
    private var responseToSendBack : List<String>? = null
    val loginResult: MutableLiveData<LoginResult> = MutableLiveData()
    private val userRepo = UserRepository()

    fun login(email: String, password: String) {
        loginResult.value = LoginResult.LOADING
        if(email.isEmpty() || password.isEmpty()){
            loginResult.value = LoginResult.INVALID_CREDENTIALS
            return
        }
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Login response ${response.body()}")
                    val responses =  response.body().toString().trim().split(",")
                    val responses0 = responses[0].split("=")
                    val responses1 = responses[1].split("=")
                    var responses2 = responses[2].split("=")
                    responses2 = responses2[1].split(")")
                    Log.d(TAG,"Responses0[1] = ${responses0[1]}")
                    Log.d(TAG,"Responses1[1] = ${responses1[1]}")
                    Log.d(TAG,"Responses2[1] = ${responses2[0]}")
                    responseToSendBack = listOf(responses0[1],responses1[1],responses2[0])
                    loginResult.value = LoginResult.SUCCESS
                } else {
                    Log.d(TAG, "Login error response ${response?.errorBody()}")
                    loginResult.value = LoginResult.INVALID_CREDENTIALS
                }

            } catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                loginResult.value = LoginResult.UNKNOWN_ERROR
            }
        }
    }

    fun getResponse(): List<String> {
        return responseToSendBack ?: emptyList()
    }
}