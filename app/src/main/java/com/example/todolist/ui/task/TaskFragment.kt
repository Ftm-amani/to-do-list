package com.example.todolist.ui.task

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.SortOrder
import com.example.todolist.data.Task
import com.example.todolist.databinding.FragmentTasksBinding
import com.example.todolist.util.exhaustive
import com.example.todolist.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_tasks), TasksAdapter.OnItemClickListener {
    private val viewModel : TasksViewModel by viewModels()

    private lateinit var searchView : SearchView
    val TAG = "onBoarding"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!onBoardingFinished()){
                viewModel.onNavigateToOnBoardingScreen()}

        val binding = FragmentTasksBinding.bind(view)

        val taskAdapter = TasksAdapter(this)


        binding.apply {
            recyclerViewTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback
            (0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val task= taskAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTaskSwiped(task)
                }
            }).attachToRecyclerView(recyclerViewTasks)

            fabAddTasks.setOnClickListener {
                viewModel.onAddNewTaskClick()
            }
        }

        setFragmentResultListener("add_edit_request"){_,bundle ->
            val result= bundle.getInt("add_edit_request")
            viewModel.onAddEditResult(result)
        }

        viewModel.tasks.observe(viewLifecycleOwner){
            taskAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect {
                event -> when(event){
                    is TasksViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(view,getString(R.string.snackbar_task_deleted), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.snackbar_undo)){
                                    viewModel.onUndoDeleteClick(event.task)
                                }.show()
                    }
                TasksViewModel.TasksEvent.NavigateToAddTaskScreen -> {
                    val action = TaskFragmentDirections.actionTaskFragmentToAddEditTaskFragment(null,getString(R.string.new_task_label))
                    findNavController().navigate(action)
                }
                is TasksViewModel.TasksEvent.NavigateToEditTaskScreen -> {
                    val action = TaskFragmentDirections.actionTaskFragmentToAddEditTaskFragment(event.task, getString(R.string.edit_task_label))
                    findNavController().navigate(action)

                }
                is TasksViewModel.TasksEvent.ShowTaskSavedConfirmationMessage -> {
                    Snackbar.make(requireView(),event.msg,Snackbar.LENGTH_SHORT).show()
                }
                TasksViewModel.TasksEvent.NavigateToDeleteAllCompletedScreen -> {
                    val action = TaskFragmentDirections.actionGlobalDeleteAllCompletedDialogFragment()
                    findNavController().navigate(action)
                }
                TasksViewModel.TasksEvent.NavigateToDeleteAllScreen -> {
                    val action = TaskFragmentDirections.actionGlobalDeleteAllDialogFragment()
                    findNavController().navigate(action)
                }
                TasksViewModel.TasksEvent.NavigateToAboutUsScreen -> {
                    val action = TaskFragmentDirections.actionTaskFragmentToAboutUsFragment()
                    findNavController().navigate(action)
                }
                TasksViewModel.TasksEvent.NavigateToOnBoardingScreen -> {
                    val action = TaskFragmentDirections.actionGlobalOnBoardingDialogFragment()
                    findNavController().navigate(action)
                }
            }.exhaustive
            }
        }

        setHasOptionsMenu(true)
    }

        override fun OnItemClick(task: Task) {
        viewModel.onTaskSelected(task)
    }

    override fun OnCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskChecked(task,isChecked)    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks,menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if(pendingQuery != null && pendingQuery.isNotEmpty()){
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery,false)
        }

        searchView.onQueryTextChanged{
            viewModel.searchQuery.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch{
            menu.findItem(R.id.action_hide_completed_tasks).isChecked =
                viewModel.preferencesFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }

            return when(item.itemId){
                R.id.action_sort_by_name -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                    true
                }

                R.id.action_sort_by_date_created -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                    true
                }

                R.id.action_hide_completed_tasks -> {
                    item.isChecked = !item.isChecked
                    viewModel.onHideCompletedClick(item.isChecked )

                    true
                }

                R.id.action_delete_all_completed_tasks -> {
                    viewModel.onDeleteAllCompletedClick()
                    true
                }

                R.id.action_delete_all_tasks -> {
                    viewModel.onDeleteAllClick()
                    true
                }
                R.id.action_about_us -> {
                    viewModel.onNavigateToAboutUs()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }
    private fun onBoardingFinished():Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("finished", false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}