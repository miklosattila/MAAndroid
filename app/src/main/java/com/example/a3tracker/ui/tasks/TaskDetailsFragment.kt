package com.example.a3tracker.ui.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a3tracker.Enums.TaskPriority
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.UsersViewModel
import com.example.a3tracker.ui.groups.GroupsViewModel
import java.text.SimpleDateFormat

class TaskDetailsFragment(taskId: Int) : Fragment() {
    private val groupsVM: GroupsViewModel by activityViewModels()
    private val taskID = taskId
    private lateinit var taskTitle: TextView
    private lateinit var statusDropdown: Spinner
    private lateinit var groupName: TextView
    private lateinit var assignedBy: TextView
    private lateinit var assignedDate: TextView
    private lateinit var assignee: TextView
    private lateinit var progress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var deadline: TextView
    private lateinit var priority: TextView
    private lateinit var lowPrioImg: ImageView
    private lateinit var mediumPrioImg: ImageView
    private lateinit var highPrioImg: ImageView
    private lateinit var description: TextView
    private lateinit var backButton: ImageView
    val filters = listOf("New","In Progress","Blocked","Done")
    private val tasksVM : TasksViewModel by activityViewModels()
    private val usersVM: UsersViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
    }

    private fun registerListeners() {
        /*statusDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = statusDropdown.selectedItem as String
                if (selectedOption == filters[0]){

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }*/

        backButton.setOnClickListener {
            replaceFragment(TasksFragment())
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initViewItems() {
        val task = tasksVM.getTaskById(taskID)
        val dateformat2 = SimpleDateFormat("MMMM dd yyyy")
        taskTitle = requireView().findViewById(R.id.taskTitle)
        taskTitle.text = task!!.title
        /*statusDropdown= requireView().findViewById(R.id.statusDropdown)
        val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,filters) }
        adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusDropdown.adapter = adapter*/
        groupName = requireView().findViewById(R.id.groupName)
        groupName.text = groupsVM.getGroupById(task.groupId)
        assignedBy = requireView().findViewById(R.id.assignedBy)
        assignedBy.text = usersVM.getName(task.createdBy)
        assignedDate = requireView().findViewById(R.id.assignedDate)
        assignedDate.text = dateformat2.format(task.createdAt)
        assignee = requireView().findViewById(R.id.assigneeName)
        assignee.text = usersVM.getName(task.assignee)
        progress = requireView().findViewById(R.id.percentDoneText)
        progress.text = task.progress.toString()
        progressBar = requireView().findViewById(R.id.progressBar)
        progressBar.progress = task.progress
        deadline = requireView().findViewById(R.id.deadlineDate)
        deadline.text = dateformat2.format(task.deadline)
        priority = requireView().findViewById(R.id.priorityText)
        lowPrioImg = requireView().findViewById(R.id.lowPrioIcon)
        mediumPrioImg = requireView().findViewById(R.id.mediumPrioIcon)
        highPrioImg = requireView().findViewById(R.id.highPrioIcon)
        when(task.priority){
            TaskPriority.LOW->{
                priority.text = "Low priority"
                lowPrioImg.visibility=View.VISIBLE
                mediumPrioImg.visibility=View.GONE
                highPrioImg.visibility=View.GONE
            }
            TaskPriority.MEDIUM->{
                priority.text = "Medium priority"
                lowPrioImg.visibility=View.GONE
                mediumPrioImg.visibility=View.VISIBLE
                highPrioImg.visibility=View.GONE
            }
            TaskPriority.HIGH->{
                priority.text = "High priority"
                lowPrioImg.visibility=View.GONE
                mediumPrioImg.visibility=View.GONE
                highPrioImg.visibility=View.VISIBLE
            }
        }
        description = requireView().findViewById(R.id.descriptionText)
        description.text= task.description
        backButton = requireView().findViewById(R.id.backButton)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}