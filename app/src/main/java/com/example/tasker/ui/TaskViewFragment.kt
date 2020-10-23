package com.example.tasker.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tasker.R
import com.example.tasker.databinding.FragmentHomeBinding
import com.example.tasker.databinding.FragmentTaskViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_task_view.view.*
import kotlinx.android.synthetic.main.fragment_task_view.*


class TaskViewFragment : Fragment() {

    private lateinit var binding: FragmentTaskViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_view, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonAddTag.setOnClickListener {
            showAddTagBottomSheetDialog()
        }

        imagePriority.setOnClickListener {
            showChangePriorityBottomSheet()
        }
    }

    private fun showChangePriorityBottomSheet() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_priority, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        dialog?.show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_more) {
            showTaskViewBottomSheetDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showTaskViewBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_task_view, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        view.layoutAddMember.setOnClickListener {
            showAddMemberBottomSheetDialog()
            dialog?.dismissWithAnimation
        }

        view.layoutTaskActivity.setOnClickListener {
            val mBottomSheetFragment = TaskHistoryFragment()
            mBottomSheetFragment.show(requireActivity().supportFragmentManager, "MY_BOTTOM_SHEET")
            dialog?.dismiss()
        }

        dialog?.show()
    }

    private fun showAddTagBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_add_tag, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        dialog?.show()
    }

    private fun showAddMemberBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_add_member, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        dialog?.show()
    }
}