package com.example.todolist.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todolist.R


class SplashFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({
            if (onBoardingFinished()) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToTaskFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment())
            }
        },
                3000)


        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("finished", false)
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