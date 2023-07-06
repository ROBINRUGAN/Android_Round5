package com.example.android_round5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android_round5.ui.mybuy.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_my_buy.*

class MyBuyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_buy)
        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        myBuy_viewPager.adapter = adapter

        TabLayoutMediator(myBuy_tabLayout, myBuy_viewPager) { tab, position ->
            // 设置每个标签的文本
            when (position) {
                0 -> tab.text = "全部"
                1 -> tab.text = "未付款"
                2 -> tab.text = "未确认"
                3 -> tab.text = "已通过"
                4 -> tab.text = "已拒绝"
            }
        }.attach()
    }
    private inner class MyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 5 // 返回标签页的数量
        }

        override fun createFragment(position: Int): Fragment {
            // 根据位置创建对应的 Fragment
            return when (position) {
                0 -> AllFragment()
                1 -> UnpaidFragment()
                2 -> UnconfirmedFragment()
                3 -> ApprovedFragment()
                4 -> RejectedFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

}