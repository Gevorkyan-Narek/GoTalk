package com.example.gotalk.view

import androidx.fragment.app.Fragment
import com.example.gotalk.viewmodel.BaseViewModel

abstract class MainFragment: Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}