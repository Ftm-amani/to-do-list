package com.example.todolist.ui.onboarding.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.R
import com.example.todolist.ui.onboarding.OnBoardingFragmentDirections
import kotlinx.android.synthetic.main.fragment_first_on_boarding.view.*
import kotlinx.android.synthetic.main.fragment_second_on_boarding.view.*

class SecondOnBoardingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second_on_boarding, container, false)

//        val viewpager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)

        view.btn_finish.setOnClickListener{
        findNavController()
            .navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToTaskFragment2())        }
        onBoardingFinished()
        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding" , Context.MODE_PRIVATE)
        val editor =sharedPref.edit()
        editor.putBoolean("finished" , true)
        editor.apply()
    }
}