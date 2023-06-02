package com.example.a3tracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a3tracker.DataClasses.UpdateProfileRequest
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.databinding.ActivityMainBinding
import com.example.a3tracker.databinding.FragmentEditProfileBinding
import com.example.a3tracker.ui.tasks.TasksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etLocation : EditText
    private lateinit var etPhoneNum : EditText
    private lateinit var etImage : EditText
    private lateinit var updateButton : Button
    private lateinit var cancelButton: Button
    private  val currentUserVM : CurrentUserViewModel by activityViewModels()
    private val profileVM: SettingsViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
    }

    private fun registerListeners() {
        updateButton.setOnClickListener{
            val updateInformation = UpdateProfileRequest(
                etLastName.text.toString(),
                etFirstName.text.toString(),
                etLocation.text.toString(),
                etPhoneNum.text.toString(),
                etImage.text.toString())
            profileVM.updateMyProfile(currentUserVM.getToken(),updateInformation)
            replaceFragment(TasksFragment())
        }
        cancelButton.setOnClickListener {
            replaceFragment(SettingsFragment())
        }
    }

    private fun initViewItems() {
        etFirstName = binding.editTextFirstName
        etFirstName.setText(currentUserVM.getFirstName())
        etLastName = binding.editTextLastName
        etLastName.setText(currentUserVM.getLastName())
        etLocation = binding.editTextLocation
        etLocation.setText(currentUserVM.getLocation())
        etPhoneNum = binding.editTextPhoneNumber
        etPhoneNum.setText(currentUserVM.getPhoneNumber())
        etImage = binding.editTextImageUrl
        etImage.setText(currentUserVM.getImageUrl())
        updateButton = binding.buttonUpdate
        cancelButton = binding.buttonCancel
    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}