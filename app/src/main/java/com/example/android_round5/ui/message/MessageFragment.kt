package com.example.android_round5.ui.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round5.R
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.adapter.MessageItemAdapter
import com.example.android_round5.databinding.FragmentMessageBinding
import com.example.android_round5.entity.HomeItem
import com.example.android_round5.entity.MessageItem

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val messageItemList = ArrayList<MessageItem>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val messageViewModel =
            ViewModelProvider(this).get(MessageViewModel::class.java)

        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initMessageItemList()
        val layoutManager = LinearLayoutManager(this.context)
        binding.messageRecyclerview.layoutManager = layoutManager
        Log.d("我要输出了？", messageItemList.toString())
        val adapter = MessageItemAdapter(messageItemList!!, this@MessageFragment)
        binding.messageRecyclerview.adapter = adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initMessageItemList() {
        repeat(20) {
            messageItemList.add(
                MessageItem(
                    R.mipmap.me_avatar,
                    "闲猫吃咸鱼",
                    "你好啊你好啊你好啊mewwwwwwwwwwwwwwwwww",
                    "昨天 13:25"
                )
            )
        }
    }

}