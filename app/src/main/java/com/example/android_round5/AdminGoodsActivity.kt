package com.example.android_round5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android_round5.ui.admingood.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_admin_goods.*

class AdminGoodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_goods)
        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        adminGoods_viewPager.adapter = adapter

        TabLayoutMediator(adminGoods_tabLayout, adminGoods_viewPager) { tab, position ->
            // 设置每个标签的文本
            when (position) {
                0 -> tab.text = "全部"
                1 -> tab.text = "未审核"
                2 -> tab.text = "已通过"
                3 -> tab.text = "已驳回"
            }
        }.attach()
    }
    private inner class MyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 4 // 返回标签页的数量
        }

        override fun createFragment(position: Int): Fragment {
            // 根据位置创建对应的 Fragment
            return when (position) {
                0 -> AdminGoodAllFragment()
                1 -> AdminGoodUnconfirmedFragment()
                2 -> AdminGoodApprovedFragment()
                3 -> AdminGoodRejectedFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}