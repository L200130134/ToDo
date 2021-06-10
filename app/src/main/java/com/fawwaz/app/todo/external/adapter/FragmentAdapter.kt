package com.fawwaz.app.todo.external.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class FragmentAdapter : FragmentStateAdapter {
    private var fragments: MutableList<Fragment> = ArrayList()
    private val ids: MutableList<Int> = ArrayList()

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {}
    constructor(fragment: Fragment) : super(fragment) {}

    fun setFragments(fragments: MutableList<Fragment>) {
        this.fragments = fragments
    }

    fun addFragment(id: Int, fragment: Fragment) {
        fragments.add(fragment)
        ids.add(id)
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun addFragment(fragment: Fragment, position: Int) {
        fragments.add(position, fragment)
    }

    fun addFragment(vararg fragments: Fragment) {
        this.fragments = fragments.toMutableList()
    }

    fun getId(position: Int): Int {
        return ids[position]
    }

    fun getIds(): List<Int> {
        return ids
    }

    @Nullable
    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }

    val firstFragment: Fragment get() = fragments[0]

    val lastFragment: Fragment get() = fragments[Math.max(0, fragments.size - 1)]

    fun removeFragment(fragment: Fragment) {
        fragments.remove(fragment)
    }

    fun notifyItemInserted() {
        this.notifyItemInserted(Math.max(0, fragments.size - 1))
    }

    fun notifyItemRemoved() {
        this.notifyItemRemoved(Math.max(0, fragments.size))
    }

    override fun createFragment(position: Int): Fragment {
        return getFragment(position)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}