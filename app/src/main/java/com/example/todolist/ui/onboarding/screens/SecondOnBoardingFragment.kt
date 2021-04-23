package com.example.todolist.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.ui.onboarding.OnBoardingFragmentDirections
import kotlinx.android.synthetic.main.fragment_second_on_boarding.view.*

class SecondOnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_second_on_boarding, container, false)

        view.btn_finish.setOnClickListener{
        findNavController()
            .navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToTaskFragment())        }
        return view
    }


}