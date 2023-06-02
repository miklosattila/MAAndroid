package com.example.a3tracker.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.a3tracker.DataClasses.CreateTaskRequest
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel


class NewTaskFragment : Fragment() {

    private val tasksVM: TasksViewModel by activityViewModels()
    private val currentUserVM: CurrentUserViewModel by activityViewModels()
    private lateinit var taskTitle: EditText
    private lateinit var taskDesc: EditText
    private lateinit var taskAssignee: EditText
    private lateinit var taskPriority: EditText
    private lateinit var taskDeadline: EditText
    private lateinit var taskDepartmentId: EditText
    private var taskStatus: Int = 0
    private lateinit var submitButton: Button
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
    }

    private fun registerListeners() {
        submitButton.setOnClickListener{
            if (taskTitle.text.isEmpty() || taskDesc.text.isEmpty()||taskAssignee.text.isEmpty()||taskPriority.text.isEmpty()||taskDeadline.text.isEmpty()||taskDepartmentId.text.isEmpty()){
                Toast.makeText(context,"Please fill all fields!",Toast.LENGTH_SHORT)
            }else{
                context?.let { tasksVM.createTask(it,currentUserVM.getToken(), CreateTaskRequest(
                    taskTitle.text.toString(),
                    taskDesc.text.toString(),
                    "${taskAssignee.text}".toInt(),
                    "${taskPriority.text}".toInt(),
                    "${taskDeadline.text}".toLong(),
                    "${taskDepartmentId.text}".toInt(),
                    taskStatus
                )) }
                replaceFragment(TasksFragment())
            }
        }
        backButton.setOnClickListener {
            replaceFragment(TasksFragment())
        }
    }

    private fun initViewItems() {
        taskTitle = requireView().findViewById(R.id.editTextTaskTitle)
        taskDesc = requireView().findViewById(R.id.editTextDescription)
        taskAssignee = requireView().findViewById(R.id.editTextAssignee)
        taskPriority = requireView().findViewById(R.id.editTextPriority)
        taskDeadline = requireView().findViewById(R.id.editTextDeadline)
        taskDepartmentId = requireView().findViewById(R.id.editTextDepartment)
        submitButton = requireView().findViewById(R.id.createTaskButton)
        backButton = requireView().findViewById(R.id.backButton)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}