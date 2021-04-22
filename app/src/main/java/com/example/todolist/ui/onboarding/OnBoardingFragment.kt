package com.example.todolist.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.ui.onboarding.screens.FirstOnBoardingFragment
import com.example.todolist.ui.onboarding.screens.SecondOnBoardingFragment
import kotlinx.android.synthetic.main.fragment_on_boarding.view.*

class OnBoardingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_on_boarding, container, false)


        val  fragmentList = arrayListOf<Fragment>(
            FirstOnBoardingFragment(),
            SecondOnBoardingFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        view.vp_onboarding.adapter = adapter


        return view
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }

 }