package com.fawwaz.app.todo.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.fawwaz.app.todo.R
import com.fawwaz.app.todo.databinding.ActivityMainBinding
import com.fawwaz.app.todo.external.adapter.FragmentAdapter
import com.fawwaz.app.todo.presentation.fragment.AddTaskDialog
import com.fawwaz.app.todo.presentation.fragment.SettingFragment
import com.fawwaz.app.todo.presentation.fragment.TaskFragment
import com.staygrateful.developers.filesid.ext.setLightNavigation


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mBinding: ActivityMainBinding
    lateinit var mPagerAdapter: FragmentAdapter

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.setLightNavigation(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        bindBottom()
        bindPager()
        bindEvent()
    }

    private fun bindBottom() {
        mBinding.layoutTask.icon.setImageResource(R.drawable.ic_date)
        mBinding.layoutSetting.icon.setImageResource(R.drawable.ic_settings)
        mBinding.layoutTask.text.text = getString(R.string.bottom_title_task)
        mBinding.layoutSetting.text.text = getString(R.string.bottom_title_setting)
    }

    private fun bindPager() {
        mPagerAdapter = FragmentAdapter(this)
        mPagerAdapter.addFragment(TaskFragment.newInstance(), SettingFragment.newInstance())
        mBinding.pager.isUserInputEnabled = false
        mBinding.pager.registerOnPageChangeCallback(onPageCallback)
        mBinding.pager.adapter = mPagerAdapter
    }

    private fun bindEvent() {
        mBinding.layoutTask.root.setOnClickListener(this)
        mBinding.layoutSetting.root.setOnClickListener(this)
        mBinding.buttonAdd.setOnClickListener(this)
    }

    private val onPageCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            doIconColorChange(position)
        }
    }

    private fun doIconColorChange(position: Int) {
        val layoutSelected = if (position == 0) mBinding.layoutTask else mBinding.layoutSetting
        val layoutUnselected = if (position == 0) mBinding.layoutSetting else mBinding.layoutTask
        val colorBlue = ContextCompat.getColor(baseContext, R.color.blue)
        val colorDark = ContextCompat.getColor(baseContext, R.color.text)

        layoutSelected.icon.setColorFilter(colorBlue)
        layoutSelected.text.setTextColor(colorBlue)
        layoutUnselected.icon.setColorFilter(colorDark)
        layoutUnselected.text.setTextColor(colorDark)
    }

    override fun onClick(view: View?) {
        when (view) {
            mBinding.buttonAdd -> {
                AddTaskDialog.show(this)
            }
            mBinding.layoutTask.root -> {
                mBinding.pager.setCurrentItem(0, false)
            }
            mBinding.layoutSetting.root -> {
                mBinding.pager.setCurrentItem(1, false)
            }
        }
    }
}