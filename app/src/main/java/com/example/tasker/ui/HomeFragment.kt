package com.example.tasker.ui


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.tasker.R
import com.example.tasker.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.bottom_sheet_add_task.*
import kotlinx.android.synthetic.main.bottom_sheet_add_task.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private var formatter = SimpleDateFormat("MMM dd", Locale.US)
    private var dueDate = "Today"
    private var priority = "Medium"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        mAuth = FirebaseAuth.getInstance()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.taskItem.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_task_view_fragment)
        }

        binding.fab.setOnClickListener {
            showAddTaskBottomSheetDialog()
        }
    }


    private fun showAddTaskBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_add_task, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        view.layout_calendar.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(Calendar.MONTH, mMonth)
                        selectedDate.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                        val date = formatter.format(selectedDate.time)
                        dueDate = date.toString()
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
                )
            }
            datePicker?.show()
        }

        view.layout_priority.setOnClickListener {
            showPriorityDialog()
        }

        view.layout_save.setOnClickListener {
            val myTask = view.editText_task.text.toString().trim()

            if (myTask.isEmpty())
            {
                editText_task.error = getString(R.string.task_required_text)
                editText_task.requestFocus()
            } else {
                // Get current date for date creation variable
                val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now()
                } else { TODO("VERSION.SDK_INT < O") }

                val creationDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))

                mDatabase = FirebaseDatabase.getInstance().reference
                val currentUser = mAuth.currentUser

                val taskData = Task.createList()
                taskData.task = myTask
                taskData.due = dueDate
                taskData.date_added = creationDate
                taskData.date_completed = null
                taskData.priority = priority
                taskData.completed = false

                val newTask = mDatabase.child("users").child(currentUser?.uid.toString()).child("tasks").push()
                taskData.uid = newTask.key
                taskData.user_id = currentUser?.uid.toString()

                newTask.setValue(taskData)

                dialog?.dismiss()
            }
        }
        dialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            findNavController().navigate(R.id.action_home_fragment_to_settings_fragment)
        }
        if (item.itemId == R.id.action_overview) {
            findNavController().navigate(R.id.action_home_fragment_to_overview_fragment)
        }
        if (item.itemId == R.id.action_filter) {
            showSortBottomSheetDialog()
        }
        if (item.itemId == R.id.action_switch_view) {
            showSwitchViewDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSortBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        dialog?.show()
    }

    private fun showSwitchViewDialog() {
        val dialog = context?.let {
            MaterialDialog(it)
                .noAutoDismiss()
                .customView(R.layout.dialog_switch_view)
        }
        dialog?.show()
    }

    private fun showPriorityDialog() {
        val dialog = context?.let {
            MaterialDialog(it)
                .noAutoDismiss()
                .customView(R.layout.dialog_priority)
        }

        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)

        dialog?.findViewById<LinearLayout>(R.id.viewUrgent)?.setOnClickListener {
            priority = "Urgent"
//            this.image_priority.setImageResource(R.drawable.ic_baseline_flag)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewHigh)?.setOnClickListener {
            priority = "High"
//            this.image_priority.setImageResource(R.drawable.ic_baseline_flag)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewDate)?.setOnClickListener {
            priority = "Medium"
//            this.image_priority.setImageResource(R.drawable.ic_baseline_flag)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewLow)?.setOnClickListener {
            priority = "Low"
//            this.image_priority.setImageResource(R.drawable.ic_baseline_flag)
            dialog.dismiss()
        }
        dialog?.show()
    }
}