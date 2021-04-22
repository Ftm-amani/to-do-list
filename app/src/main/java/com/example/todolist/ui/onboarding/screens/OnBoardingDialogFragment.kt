package com.example.todolist.ui.onboarding.screens

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingDialogFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_onboarding_title))
                    .setMessage(getString(R.string.dialog_onboarding_msg))
                    .setNegativeButton(getString(R.string.dialog_onboarding_no), null)
                    .setPositiveButton(getString(R.string.dialog_onboarding_yes)) { _, _ ->
                        onConfirmClick()
                    }
                    .create()


    private fun onConfirmClick(){
        val action = OnBoardingDialogFragmentDirections.actionOnBoardingDialogFragmentToOnBoardingFragment()
        findNavController().navigate(action)

        }
}
