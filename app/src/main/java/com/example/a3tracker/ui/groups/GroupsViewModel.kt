package com.example.a3tracker.ui.groups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.DataClasses.GetDepartmentsResponse
import com.example.a3tracker.Repo.DepartmentsRepository
import kotlinx.coroutines.launch

data class AllGroups(
    var groups: MutableList<GetDepartmentsResponse> = mutableListOf()
)

class GroupsViewModel : ViewModel() {

    private val _uistate = MutableLiveData(AllGroups())

    fun getGroupById(id:Int):String?{
        for(g in _uistate.value!!.groups){
            if (g.departmentId==id){
                return g.departmentName
            }
        }
        return null
    }

    fun getDepartments(token: String) {
        val groupsRepo = DepartmentsRepository()
        viewModelScope.launch {
            try {
                val response = groupsRepo.getDepartments(token = token)
                if (response!=null) {
                    for(r in response){
                        val tempId=r.departmentId
                        val tempName=r.departmentName
                        _uistate.value?.groups!!.add(GetDepartmentsResponse(tempId,tempName))
                    }
                }
            } catch (ex: Exception) {
                Log.i("EXCEPTION", ex.message, ex)
            }
        }
    }
}