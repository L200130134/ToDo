package com.fawwaz.app.todo.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.fawwaz.app.todo.data.room.model.ToDoModel
import com.fawwaz.app.todo.data.room.repo.TodoViewModel
import com.fawwaz.app.todo.databinding.FragmentDialogAddBinding
import com.fawwaz.app.todo.external.constant.AppConstant
import com.fawwaz.app.todo.external.extension.getDateString
import com.fawwaz.app.todo.external.extension.getUserId
import com.fawwaz.app.todo.external.helper.Date
import com.fawwaz.app.todo.external.helper.clone
import com.fawwaz.app.todo.presentation.base.BaseDialog
import com.staygrateful.developers.filesid.ext.showToast
import java.util.*

class AddTaskDialog : BaseDialog(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mBinding : FragmentDialogAddBinding
    private lateinit var mTodoViewModel : TodoViewModel
    private var choosedCalendar : Calendar? = null
    private var model: ToDoModel? = null
    private var taskStatus: Int = AppConstant.Task.TASK_STATUS_NOT_COMPLETED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = it.getParcelable(KEY_TODO_MODEL)
        }
    }

    override fun getContentView(inflater: LayoutInflater): View {
        mBinding = FragmentDialogAddBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObject()
        bindData()
        bindEvent()
    }

    private fun bindObject() {
        val viewModel : TodoViewModel by viewModels() {
            TodoViewModel.ToDoViewModelFactory(context)
        }
        mTodoViewModel = viewModel
    }

    private fun bindData() {
        model?.let {
            taskStatus = it.status
            mBinding.inputTitle.setText(it.title)
            mBinding.inputDescription.setText(it.description)
            mBinding.status.text = getTextFromStatus(taskStatus)
            mBinding.datePicker.text = it.dateId
        }
        val visibleStatus = if (model == null) View.GONE else View.VISIBLE
        val text = if (model == null) "Create Task" else "Save Task"
        mBinding.titleStatus.visibility = visibleStatus
        mBinding.status.visibility = visibleStatus
        mBinding.buttonDelete.visibility = visibleStatus
        mBinding.buttonCreate.setText(text)
    }

    private fun bindEvent() {
        mBinding.buttonCancel.setOnClickListener(this)
        mBinding.buttonCreate.setOnClickListener(this)
        mBinding.status.setOnClickListener(this)
        mBinding.buttonDelete.setOnClickListener(this)
        mBinding.datePicker.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == mBinding.buttonCancel) {
            dismiss()
        } else if (view == mBinding.buttonCreate) {
            createTask()
        } else if (view == mBinding.datePicker) {
            pickDate()
        } else if (view == mBinding.status) {
            toggleStatus()
        } else if (view == mBinding.buttonDelete) {
            deleteTask()
        }
    }

    private fun deleteTask() {
        model?.let {
            mTodoViewModel.delete(it)
            dismiss()
        }
    }

    private fun toggleStatus() {
        model?.let {
            val isCompleted = taskStatus == AppConstant.Task.TASK_STATUS_COMPLETED
            val status = if (isCompleted) AppConstant.Task.TASK_STATUS_NOT_COMPLETED else
                AppConstant.Task.TASK_STATUS_COMPLETED
            taskStatus = status
            mBinding.status.text = getTextFromStatus(taskStatus)
        }
    }

    private fun getTextFromStatus(status: Int) : String {
        val isCompleted = status == AppConstant.Task.TASK_STATUS_COMPLETED
        return if (isCompleted) "Completed" else "Not Completed"
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        choosedCalendar = Date.getCalendar(year, monthOfYear, dayOfMonth)
        mBinding.datePicker.text = getDateString(choosedCalendar!!.timeInMillis)
    }

    private fun createTask() {
        val title = mBinding.inputTitle.text.toString()
        val desc = mBinding.inputDescription.text.toString()
        val date = mBinding.datePicker.text.toString().trim()
        val userId = getUserId(context)
        if (userId == null) {
            showToast("User id invalid!")
            return
        }
        if (title.isEmpty()) {
            showToast("Title cannot empty!")
            return
        }
        if (desc.isEmpty()) {
            showToast("Description cannot empty!")
            return
        }
        if (date.isEmpty()) {
            showToast("Please choose date!")
            return
        }
        if (model != null) {
            model?.clone()?.let {
                it.title = title
                it.description = desc
                it.dateId = date
                it.status = taskStatus
                mTodoViewModel.update(it)
            }
        } else {
            mTodoViewModel.insert(
                ToDoModel(0, userId, date, title, desc, choosedCalendar!!.timeInMillis)
            )
        }
        dismiss()
    }

    private fun pickDate() {
        Date.showDatePicker(requireContext(), this)
    }

    companion object {
        private const val KEY_TODO_MODEL = "key_todo_model"
        @JvmStatic
        fun show(activity: FragmentActivity, toDoModel: ToDoModel? = null) = AddTaskDialog().apply {
            val fm = activity.supportFragmentManager
            val tag = activity::class.java.simpleName
            arguments = Bundle().apply {
                putParcelable(KEY_TODO_MODEL, toDoModel)
            }
            if (fm.findFragmentByTag(tag) == null) {
                show(fm, tag)
            }
        }
    }
}