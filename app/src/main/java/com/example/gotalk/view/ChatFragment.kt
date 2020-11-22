package com.example.gotalk.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gotalk.R
import com.example.gotalk.data.storage.FragmentNavigator
import com.example.gotalk.data.storage.OnItemClick
import com.example.gotalk.data.storage.SmoothScrollToPositionCallback
import com.example.gotalk.databinding.ChatBinding
import com.example.gotalk.view.adapter.ChatAdapter
import com.example.gotalk.viewmodel.ChatViewModel

class ChatFragment : MainFragment(), SmoothScrollToPositionCallback, FragmentNavigator {

    override val viewModel = ChatViewModel()
    lateinit var binding: ChatBinding
    private val chatAdapter = ChatAdapter(viewModel.userList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.chatRecycler.adapter = chatAdapter
        viewModel.onCreateView()
        binding.executePendingBindings()
        viewModel.recipientList.observe(this, {
            it?.let {
                Log.d("OnCreateView", it.toString())
                binding.chatRecycler.adapter?.notifyDataSetChanged()
            }
        })
        return binding.root
    }

    override fun scrollToPos(position: Int) {
            binding.chatRecycler.smoothScrollToPosition(position)
    }

    override fun navigateTo(vararg params: String) {
        val dialogFragment = DialogFragment()
        dialogFragment.arguments = Bundle().apply {
            putString("recipientId", params.first())
        }

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment, dialogFragment)?.addToBackStack("ChatFragment")
            ?.commit()
    }

    override fun navigateToBack() {
        activity?.finish()
    }
}