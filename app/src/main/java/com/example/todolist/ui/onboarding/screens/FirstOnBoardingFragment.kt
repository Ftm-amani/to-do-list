package com.example.todolist.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.R
import kotlinx.android.synthetic.main.fragment_first_on_boarding.view.*

class FirstOnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_on_boarding, container, false)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)

        view.btn_next.setOnClickListener{
            viewpager?.currentItem = 1
        }
        return view
    }
}