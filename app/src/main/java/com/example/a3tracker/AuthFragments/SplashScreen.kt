package com.example.a3tracker.AuthFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.a3tracker.Activities.MainActivity
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.R
import java.util.Date

const val BASE_URL = "https://tracker-3track.a2hosted.com/"


class SplashScreen : Fragment() {

    private val currentUserViewModel : CurrentUserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("TRACKER", Context.MODE_PRIVATE)
        val retrievedToken = sharedPreferences.getString("token",null)
        val retrievedDeadline = sharedPreferences.getLong("deadline",12345678)
        Log.i("Splash Screen token",retrievedToken.toString())
        Log.i("Splash Screen deadline",retrievedDeadline.toString())
        Log.i("Splash Screen time ",Date().time.toString())
        if(Date().time < retrievedDeadline){
            currentUserViewModel.updateLoginResponse(retrievedDeadline,retrievedToken.toString(),0)
            if (currentUserViewModel.getCurrentUser()){
                startActivity(Intent(activity,MainActivity::class.java))
            }else{
                findNavController().navigate(R.id.action_splashScreen_to_loginScreen)
            }
        }else{
            findNavController().navigate(R.id.action_splashScreen_to_loginScreen)
        }
    }
}