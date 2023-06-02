package com.example.a3tracker.AuthFragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a3tracker.Activities.MainActivity
import com.example.a3tracker.Enums.LoginResult
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.ViewModels.LoginViewModel

class LoginScreen : Fragment() {

    private lateinit var showPwImg : ImageView
    private lateinit var hidePwImg : ImageView
    private lateinit var passwordText : EditText
    private lateinit var emailText : EditText
    private lateinit var loginButton : Button
    private val loginVM: LoginViewModel by activityViewModels()
    private val currentUserViewModel : CurrentUserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        registerListeners()
    }

    private fun initViewItems() {
        showPwImg = requireView().findViewById(R.id.imageViewShowPW)
        hidePwImg = requireView().findViewById(R.id.imageViewHidePW)
        hidePwImg.visibility = View.GONE
        passwordText = requireView().findViewById(R.id.editTextPassword)
        loginButton = requireView().findViewById(R.id.buttonSignIn)
        emailText = requireView().findViewById(R.id.editTextEmailAddress)
    }

    private fun registerListeners(){

        loginButton.setOnClickListener{
            if(emailText.text.toString().isEmpty() || passwordText.text.toString().isEmpty()){
                Toast.makeText(activity,"Please fill in all fields!",Toast.LENGTH_SHORT).show()
            }else{
                loginVM.login(emailText.text.toString(),passwordText.text.toString())
                loginVM.loginResult.observe(viewLifecycleOwner){
                    if(it == LoginResult.LOADING){
                        return@observe
                    }
                    if (it == LoginResult.SUCCESS){
                        val response = loginVM.getResponse()
                        //Log.i("LOGIN RESPONSES","${response[0]} ${response[1]} ${response[2]}")
                        currentUserViewModel.updateLoginResponse(response[0].toLong(),response[1],response[2].toInt())
                        currentUserViewModel.getCurrentUser()
                        val sharedPreferences = requireActivity().getSharedPreferences("TRACKER",Context.MODE_PRIVATE)
                        sharedPreferences.edit().putString("token",currentUserViewModel.getToken()).apply()
                        sharedPreferences.edit().putLong("deadline",currentUserViewModel.getDeadline()).apply()
                        startActivity(Intent(activity,MainActivity::class.java))
                    }
                    else if(it == LoginResult.INVALID_CREDENTIALS){
                        Toast.makeText(activity,"Invalid email or password!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        showPwImg.setOnClickListener{
            hidePwImg.visibility = View.VISIBLE
            showPwImg.visibility = View.GONE
            passwordText.transformationMethod = null
        }

        hidePwImg.setOnClickListener{
            showPwImg.visibility = View.VISIBLE
            hidePwImg.visibility = View.GONE
            passwordText.transformationMethod = PasswordTransformationMethod()
        }

        //back button
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(activity,"Where do you want to go back? ☺",Toast.LENGTH_SHORT).show()
            }
        })
    }
}