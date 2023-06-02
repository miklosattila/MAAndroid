package com.example.a3tracker.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.DataClasses.UpdateProfileRequest
import com.example.a3tracker.Repo.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val userRepo = UserRepository()

    fun updateMyProfile(token:String,updateInformations: UpdateProfileRequest){
        viewModelScope.launch {
            try {
                val response = userRepo.updateProfile(token,updateInformations)
                if(response?.isSuccessful==true){
                    Log.d("Edit Profile",response.body().toString())
                }
            }catch (ex:Exception){
                Log.d("Exception",ex.message,ex)
            }
        }
    }
}