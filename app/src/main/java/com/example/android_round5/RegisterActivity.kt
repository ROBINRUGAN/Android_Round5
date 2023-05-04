package com.example.android_round5

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.getCode
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var mTimeButton: Button? = null
    private var time: RegisterActivity.TimeCount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        //在需要启动的 activity 中开启动画的特征
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        window.enterTransition = Explode()
//        window.enterTransition = Slide(Gravity.RIGHT)
//        window.enterTransition = Fade()
//        window.exitTransition = Explode()
//        window.exitTransition = Slide()
//        window.exitTransition = Fade()

        setContentView(R.layout.activity_register)

        /**
         * 这部分用来监听验证码的倒计时，并设置时间
         */
        mTimeButton = getCode
        time = TimeCount(60000, 1000)
        mTimeButton!!.setOnClickListener {
            time!!.start()
        }

        /**
         * 注册成功就直接进入首页，无需重复登录
         */
        register.setOnClickListener{
            val intent =Intent(this@RegisterActivity,MainActivity::class.java)
            this@RegisterActivity.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    /**
     * 验证码按钮显示倒计时类，继承自倒计时类
     *
     * @author MEWWW
     *
     * @param millisInFuture
     * 总倒计时长 毫秒
     * @param countDownInterval
     * 倒计时间隔
     */
    internal inner class TimeCount
        (millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            mTimeButton!!.isEnabled = false
            mTimeButton!!.text = (millisUntilFinished / 1000).toString() + "秒后可重发"
        }

        override fun onFinish() { // 计时结束
            mTimeButton!!.isEnabled = true
            mTimeButton!!.text = "重新获取"
        }
    }
    override fun onPause() {
        super.onPause()
        object : CountDownTimer(2300, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("MEWWW", millisUntilFinished.toString())
            }

            override fun onFinish() {
                finish()
            }
        }.start()
    }
}