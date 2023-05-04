package com.example.android_round5

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_round5.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        navView.setupWithNavController(navController)

    }
}
