package com.example.todolist.ui.deleteallcompleted

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todolist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val viewModel : DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.menu_dialog_delete_all_completed_title))
                    .setMessage(getString(R.string.menu_dialog_delete_all_completed_msg))
                    .setNegativeButton(R.string.menu_dialog_delete_all_cancel, null)
                    .setPositiveButton(R.string.menu_dialog_delete_all_yes) { _ , _ ->
                        viewModel.onConfirmClick()
                    }
                    .create()
}