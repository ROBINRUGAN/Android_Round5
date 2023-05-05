package com.example.android_round5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_round5.R
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.databinding.FragmentHomeBinding
import com.example.android_round5.entity.HomeItem


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeItemList = ArrayList<HomeItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        /**
         * 利用viewModel来处理动态数据
         */
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        initHomeItemList()
        val layoutManager = GridLayoutManager(this.context, 2)
        binding.homeRecyclerview.layoutManager = layoutManager
        Log.d("我要输出了？", homeItemList.toString())
        val adapter = HomeItemAdapter(homeItemList!!, this@HomeFragment)
        binding.homeRecyclerview.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initHomeItemList()
    {
        repeat(10)
        {
            homeItemList.add(
                HomeItem(
                    R.mipmap.mew,
                    "这是一个标题",
                    114514.00,
                    100,
                    R.mipmap.mew,
                    "闲猫吃咸鱼"
                )
            )
        }
    }
}