package com.fawwaz.app.todo.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fawwaz.app.todo.databinding.FragmentSettingBinding
import com.fawwaz.app.todo.external.extension.getAccount
import com.fawwaz.app.todo.external.extension.getSignInClient
import com.fawwaz.app.todo.presentation.activity.AuthActivity
import com.staygrateful.developers.filesid.ext.showToast


class SettingFragment : Fragment() {
    private lateinit var mBinding : FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentSettingBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvent()
    }

    private fun bindEvent() {
        mBinding.buttonSignout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        getSignInClient(context)?.signOut()
        val account = getAccount(context)
        if (account == null) {
            val activity = activity
            activity?.startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        } else {
            showToast("Cannot sign out!")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}