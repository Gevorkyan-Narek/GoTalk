package com.example.gotalk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.gotalk.R
import com.example.gotalk.data.storage.FragmentNavigator
import com.example.gotalk.data.storage.SmoothScrollToPositionCallback
import com.example.gotalk.databinding.DialogBinding
import com.example.gotalk.viewmodel.DialogViewModel

class DialogFragment : MainFragment(), FragmentNavigator, SmoothScrollToPositionCallback {

    override val viewModel: DialogViewModel = DialogViewModel(this, this)
    lateinit var binding: DialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipientId = arguments?.getString("recipientId")
        viewModel.recipientId = recipientId!!
        viewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog, container, false)
        binding.viewModel = viewModel
        binding.dialogListView.adapter = viewModel.dialogAdapter
        return binding.root
    }

    override fun navigateTo(vararg params: String) {

    }

    override fun navigateToBack() {
        fragmentManager?.popBackStack()
    }

    override fun scrollToPos(position: Int) {
        binding.dialogListView.smoothScrollToPosition(viewModel.dialogAdapter.itemCount)
    }
}