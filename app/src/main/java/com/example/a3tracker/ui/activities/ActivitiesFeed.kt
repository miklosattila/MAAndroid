package com.example.a3tracker.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.a3tracker.R
import com.example.a3tracker.ViewModels.CurrentUserViewModel
import com.example.a3tracker.databinding.FragmentActivitiesFeedBinding
import com.example.a3tracker.databinding.FragmentGroupsBinding
import com.example.a3tracker.ui.groups.GroupsViewModel

class ActivitiesFeed : Fragment() {

    private val currentUserVM : CurrentUserViewModel by activityViewModels()
    private lateinit var button : Button
    private var _binding: FragmentActivitiesFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivitiesFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}