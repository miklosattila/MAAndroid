package com.example.a3tracker.ViewModels

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.DataClasses.GetCUResponse
import com.example.a3tracker.Repo.UserRepository
import kotlinx.coroutines.launch

data class AllUsers(
    var users : MutableList<GetCUResponse> = mutableListOf()
)

class UsersViewModel : ViewModel() {
    private val _uistate = MutableLiveData(AllUsers())


    fun getAllUsers(token : String) {
        val userRepo = UserRepository()
        //Log.i("GET USERS","test")
        viewModelScope.launch {
            try {
                val response = userRepo.getUsers(usersRequest = token)
                if (response.isSuccessful) {
                    val responses = response.body().toString().trim().split("),")
                    for (r in responses) {
                        val currentResponse = r.split(",")
                        val tempID = currentResponse[0].split("=").get(1)
                        val tempLastName = currentResponse[1].split("=").get(1)
                        val tempFirstName = currentResponse[2].split("=").get(1)
                        val tempEmail = currentResponse[3].split("=").get(1)
                        val tempType = currentResponse[4].split("=").get(1)
                        val tempLocation = currentResponse[5].split("=").get(1)
                        val tempPN = currentResponse[6].split("=").get(1)
                        val tempDepId = currentResponse[7].split("=").get(1)
                        val tempImageUrl = currentResponse[8].split("=").get(1).dropLast(1)
                        _uistate.value!!.users.add(
                            GetCUResponse(
                                tempID.toInt(),
                                tempLastName,
                                tempFirstName,
                                tempEmail,
                                tempType.toInt(),
                                tempLocation,
                                tempPN,
                                tempDepId.toInt(),
                                tempImageUrl
                            )
                        )
                        //Log.i("CurrentResponse",currentResponse.toString())
                    }
                    /*for (u in _uistate.value!!.users) {
                        Log.i("USERVM", u.toString())
                    }*/
                }
            } catch (ex: Exception) {
                Log.i("UsersViewModel", ex.message, ex)
            }
        }
    }



    fun getName(ID: Int):String?{
        for (u in _uistate.value!!.users){
            if (u.ID == ID){
                return "${u.first_name}  ${u.last_name}"
            }
        }
        return null
    }

    fun getImage(ID:Int):String?{
        for (u in _uistate.value!!.users){
            if (u.ID == ID){
                return u.image
            }
        }
        return null
    }

    fun getUserByDepartmentAndType(departmentId:Int,type: Int):GetCUResponse?{
        for(u in _uistate.value!!.users){
            /*Log.i("U FULL USER",u.toString())
            Log.i("U DEPARTMENT ID",u.department_id.toString())
            Log.i("U type",u.type.toString())*/
            if(u.department_id==departmentId && u.type==type){
                return u
            }
        }
        return null
    }
}
