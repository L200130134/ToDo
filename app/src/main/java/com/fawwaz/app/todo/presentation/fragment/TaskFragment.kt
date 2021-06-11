package com.fawwaz.app.todo.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fawwaz.app.todo.R
import com.fawwaz.app.todo.data.room.model.ToDoModel
import com.fawwaz.app.todo.data.room.repo.TodoViewModel
import com.fawwaz.app.todo.databinding.FragmentTaskBinding
import com.fawwaz.app.todo.external.adapter.DateAdapter
import com.fawwaz.app.todo.external.adapter.TaskAdapter
import com.fawwaz.app.todo.external.extension.getDateString
import com.fawwaz.app.todo.presentation.activity.AuthActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class TaskFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding : FragmentTaskBinding
    private lateinit var mDateAdapter : DateAdapter
    private lateinit var mTaskAdapter : TaskAdapter

    private var mAccount : GoogleSignInAccount? = null
    private var mTodoViewModel : TodoViewModel? = null
    private var lastPosition = 0
    private var lastDateId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTaskBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authValidate()
        bindObject()
        bindListDate()
        bindListTask()
        bindEvent()
    }

    private fun authValidate() {
        val activity = this.activity
        mAccount = GoogleSignIn.getLastSignedInAccount(activity)
        if (mAccount == null) {
            activity?.startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        } else {
            updateUserUI(mAccount!!)
        }
    }

    private fun updateUserUI(account: GoogleSignInAccount) {
        mBinding.textName.text = account.displayName
        context?.let {
            Glide.with(it).load(account.photoUrl)
                .placeholder(R.drawable.placeholder_account)
                .error(R.drawable.placeholder_account)
                .circleCrop()
                .into(mBinding.iconProfile)
        }
    }

    private fun bindObject() {
        val viewModel : TodoViewModel by viewModels {
            TodoViewModel.ToDoViewModelFactory(context)
        }
        mTodoViewModel = viewModel
    }

    private fun bindListDate() {
        mDateAdapter = DateAdapter(mEventListener)
        mBinding.content.listDate.layoutManager = LinearLayoutManager(
            context, RecyclerView.HORIZONTAL, false
        )
        mBinding.content.listDate.setHasFixedSize(true)
        mBinding.content.listDate.isNestedScrollingEnabled = false
        mBinding.content.listDate.itemAnimator = null
        mBinding.content.listDate.adapter = mDateAdapter
        mBinding.content.listDate.addOnScrollListener(mDateScrollListener)
        resetScrollPosition()
    }

    private fun bindListTask() {
        mTaskAdapter = TaskAdapter(mTaskEventListener)
        mBinding.content.listTask.layoutManager = LinearLayoutManager(context)
        mBinding.content.listTask.setHasFixedSize(true)
        mBinding.content.listTask.isNestedScrollingEnabled = false
        mBinding.content.listTask.itemAnimator = null
        mBinding.content.listTask.adapter = mTaskAdapter
    }

    private fun bindEvent() {
        mBinding.textTaskCount.setOnClickListener(this)
        mBinding.iconProfile.setOnClickListener(this)
    }

    private fun resetScrollPosition(smoothScroll: Boolean = false) {
        mBinding.content.listDate.post {
            if (smoothScroll) {
                mBinding.content.listDate.smoothScrollToPosition(mDateAdapter.centerScrollPosition())
            } else {
                mBinding.content.listDate.scrollToPosition(mDateAdapter.centerScrollPosition())
            }
        }
    }

    override fun onClick(view: View?) {
        if (view == mBinding.textTaskCount) {
            mDateAdapter.resetSelectedItem()
            resetScrollPosition(true)
        }
    }

    private val mDateScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val lm = recyclerView.layoutManager
                if (lm is LinearLayoutManager) {
                    mDateAdapter.firstItemPosition = lm.findFirstVisibleItemPosition()
                }
            }
        }
    }

    private val mEventListener = object : DateAdapter.EventListener {
        override fun onClick(position: Int, validPosition: Int, timestamp: Long, view: View?) {
            val userId = mAccount?.email
            if (userId != null) {
                val date = getDateString(timestamp).trim()
                mBinding.content.textDate.text = date
                mTodoViewModel?.let { tvm ->
                    lastDateId?.let {
                        tvm.getLiveData(userId, it).removeObserver(mObserver)
                    }
                    tvm.getLiveData(userId, date).observe(viewLifecycleOwner, mObserver)
                }

                lastPosition = validPosition
                lastDateId = date
            }
        }
    }

    private val mTaskEventListener = object : TaskAdapter.EventListener {
        override fun onClick(model: ToDoModel?, position: Int, view: View?) {
            AddTaskDialog.show(activity!!, model)
        }

        override fun onCompletedChange(model: ToDoModel?, view: View?) {
            if (model != null) {
                mTodoViewModel?.update(model)
            }
        }
    }

    private val mObserver = Observer<MutableList<ToDoModel>> {
        val d1 = mBinding.content.textDate.text.toString()
        if (it.isNotEmpty()) {
            val d2 = it[0].dateId
            if (d1 != d2) {
                //showToast("Invalid : $d1 | ${d2}")
                return@Observer
            }
            mDateAdapter.putMapper(d1, it.size)
        } else {
            mDateAdapter.removeMapper(d1)
        }
        mDateAdapter.notifyItemChanged(mDateAdapter.lastSelectedPosition)
        mTaskAdapter.isCompleted = true
        mTaskAdapter.submit(it)
        doChangeDate(it.size)
    }

    private fun doChangeDate(size: Int) {
        val dateId = getDateString(mDateAdapter.currentTimePosition)
        val currentDateId = getDateString(System.currentTimeMillis())
        if (dateId == currentDateId) {
            val countInMap = mDateAdapter.getCount(currentDateId)
            val count = if (countInMap > 0) countInMap else size
            mBinding.textTaskCount.text = "$count Task for Today"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskFragment()
    }
}