package com.example.android_round5

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_round5.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
//        在需要启动的 activity 中开启动画的特征
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        window.enterTransition = Explode()
//        window.enterTransition = Slide(Gravity.RIGHT)
//        window.enterTransition = Fade()
//        window.exitTransition = Explode()
//        window.exitTransition = Slide()
//        window.exitTransition = Fade()
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // 去除菜单项图标的着色效果
        navView.itemIconTintList = null
//获取中间的itemView
        val itemView: BottomNavigationItemView = binding.navView.findViewById(R.id.navigation_upload)



//修改未选中时的图标大小
        itemView.setShifting(false)
        itemView.setIconSize(150)






        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_deal,
                R.id.navigation_upload,
                R.id.navigation_message,
                R.id.navigation_me
            )
        )
        navView.setupWithNavController(navController)


    }
}
