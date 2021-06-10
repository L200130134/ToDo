package com.fawwaz.app.todo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.fawwaz.app.todo.databinding.ActivityMainBinding
import com.fawwaz.app.todo.external.adapter.FragmentAdapter

class MainActivity : AppCompatActivity() {

    lateinit var mBinding : ActivityMainBinding
    lateinit var mPagerAdapter : FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mPagerAdapter = FragmentAdapter(this)
        mPagerAdapter.addFragment(TaskFragment.newInstance(), SettingFragment.newInstance())
        mBinding.pager.registerOnPageChangeCallback(onPageCallback)
        mBinding.pager.adapter = mPagerAdapter

    }

    private val onPageCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

        }
    }
}