package com.example.a3tracker.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.a3tracker.Activities.AuthActivity
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.ViewModels.UsersViewModel
import com.example.a3tracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var textName : TextView
    private lateinit var profilePicture : ImageView
    private lateinit var logOut : Button
    private lateinit var emailText : TextView
    private lateinit var phonenumText : TextView
    private lateinit var locationText : TextView
    private lateinit var mentoredBy : TextView
    private lateinit var mentorPicture : ImageView
    private lateinit var mentorName : TextView
    private lateinit var roleText : TextView
    private lateinit var editProfile : ImageView
    private val currentUserVM : CurrentUserViewModel by activityViewModels()
    private val allUsersVM : UsersViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
    }

    private fun registerListeners() {
        logOut.setOnClickListener{
            val prefs = requireActivity().getSharedPreferences("TRACKER", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            startActivity(Intent(activity,AuthActivity::class.java))
        }
        editProfile.setOnClickListener{
            replaceFragment(EditProfileFragment())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewItems() {
        val mentor = allUsersVM.getUserByDepartmentAndType(currentUserVM.getDepartmentId(),0)
        if (mentor != null) {
            currentUserVM.updateMentor(mentor)
        }
        Log.i("MENTOR",mentor.toString())
        Log.i("Department ID", currentUserVM.getDepartmentId().toString())

        textName = requireView().findViewById(R.id.textName)
        textName.text = currentUserVM.getName()

        profilePicture = _binding!!.profilePicture
        Glide.with(activity).load(currentUserVM.getImageUrl()).into(profilePicture)

        logOut =_binding!!.logOutButton

        emailText = _binding!!.textEmail
        emailText.text = currentUserVM.getEmail()

        phonenumText = _binding!!.textPhoneNumber
        phonenumText.text = currentUserVM.getPhoneNumber()

        locationText = _binding!!.textLocation
        locationText.text = "Office Location: ${currentUserVM.getLocation()}"

        mentoredBy = _binding!!.textUsersMentor
        mentoredBy.text = "${currentUserVM.getName()}'s mentor"

        mentorName = _binding!!.textMentorName
        mentorName.text = allUsersVM.getName(mentor!!.ID)

        mentorPicture = _binding!!.imageMentor
        Glide.with(activity).load(allUsersVM.getImage(mentor.ID)).into(mentorPicture)

        roleText = _binding!!.textRole
        roleText.text = "Software Developer"

        if(currentUserVM.getType()==0){
            roleText.text = "Mentor"
            mentoredBy.visibility = View.GONE
            mentorName.visibility = View.GONE
            mentorPicture.visibility  = View.GONE
        }

        editProfile = _binding!!.editProfile
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}