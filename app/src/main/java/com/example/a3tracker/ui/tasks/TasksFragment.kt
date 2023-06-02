package com.example.a3tracker.ui.tasks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.a3tracker.Activities.MainActivity
import com.example.a3tracker.DataClasses.Tasks
import com.example.a3tracker.Enums.TaskPriority
import com.example.a3tracker.Enums.TaskStatus
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.ViewModels.UsersViewModel
import com.example.a3tracker.databinding.FragmentTasksBinding
import com.example.a3tracker.ui.groups.GroupsViewModel
import com.example.a3tracker.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class TasksFragment : Fragment() {

    private val currentUserVM : CurrentUserViewModel by activityViewModels()
    private val allUsersVM : UsersViewModel by activityViewModels()
    private val tasksVM: TasksViewModel by activityViewModels()
    private val groupsVM: GroupsViewModel by activityViewModels()
    private lateinit var filterDropdown : Spinner
    private val filters = mutableListOf("Recently Added","Active tasks","Closed tasks")
    private lateinit var newTaskButton : ImageView
    private lateinit var profile: ImageView
    private lateinit var adapter: TasksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tasksArrayList: MutableList<Tasks>
    private lateinit var taskDetails : ImageView
    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sharedPreferences = requireActivity().getSharedPreferences("TRACKER", Context.MODE_PRIVATE)
        val retrievedToken = sharedPreferences.getString("token",null)
        val retrievedDeadline = sharedPreferences.getLong("deadline",12345678)
        currentUserVM.updateLoginResponse(retrievedDeadline,retrievedToken.toString(),0)
        currentUserVM.getCurrentUser()
        Log.i("TASKS FRAGMENT",currentUserVM.getName())
        allUsersVM.getAllUsers(currentUserVM.getToken())
        Log.i("TASKS FRAGMENT","GET ALL USERS")
        tasksVM.getMyTasks(currentUserVM.getToken())
        Log.i("TASKS FRAGMENT","GET MY TASKS")
        groupsVM.getDepartments(currentUserVM.getToken())
        Log.i("TASKS FRAGMENT","GET GROUPS")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
        val layoutManager = LinearLayoutManager(context)
        //Log.i("NUMBEROFT",tasksnr.toString())
        tasksArrayList = tasksVM.getTasks()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = TasksAdapter(object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int,taskId : Int) {
                replaceFragment(TaskDetailsFragment(taskId))
            }
        },tasksArrayList,allUsersVM,groupsVM)
        recyclerView.adapter = adapter
    }

    private fun registerListeners() {
        filterDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = filterDropdown.selectedItem as String
                if (selectedOption == filters[0]){
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        newTaskButton.setOnClickListener {
            replaceFragment(NewTaskFragment())
        }

        profile.setOnClickListener {
            replaceFragment(SettingsFragment())
        }

//        taskDetails.setOnClickListener {
//            replaceFragment(TaskDetailsFragment(recyclerView.id.))
//        }

    }

    private fun initViewItems() {
        filterDropdown = _binding!!.filterSpinner
        val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,filters) }
        adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterDropdown.adapter = adapter
        newTaskButton = _binding!!.newTaskButton
        profile = _binding!!.profilePicture
        Glide.with(activity).load(currentUserVM.getImageUrl()).into(profile)
        //taskDetails = _binding!!.recyclerView.findViewById(R.id.taskDetailsButton)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}