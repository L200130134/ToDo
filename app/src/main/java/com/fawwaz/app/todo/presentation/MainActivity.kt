package com.fawwaz.app.todo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fawwaz.app.todo.R
import com.fawwaz.app.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}