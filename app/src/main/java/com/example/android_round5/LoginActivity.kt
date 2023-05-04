package com.example.android_round5

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.code
import kotlinx.android.synthetic.main.activity_login.getCode
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.phone
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private var mTimeButton: Button? = null
    private var time: TimeCount? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**
         * 这部分用来监听验证码的倒计时，并设置时间
         */
        mTimeButton = getCode
        time = TimeCount(60000, 1000)
        mTimeButton!!.setOnClickListener {
            time!!.start()
        }

        /**
         * 跳转去注册页
         */
        gotoRegister.setOnClickListener {
            Toast.makeText(this@LoginActivity, "准备为你跳转注册页", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            this@LoginActivity.startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )

        }


        /**
         * 跳转进入主程序，并落在主页
         */
        login.setOnClickListener{
            Toast.makeText(this@LoginActivity, "欢迎进入MewStore!!ヾ(≧▽≦*)o", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            this@LoginActivity.startActivity(
                intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }

        /**
         * 改变登录方式
         */
        changeLoginWay.setOnClickListener {
            if (changeLoginWay.text.equals("用户名密码登录")) {
                phone.visibility = INVISIBLE
                usernmae.visibility = VISIBLE
                code.visibility = INVISIBLE
                password.visibility = VISIBLE
                getCode.visibility = INVISIBLE
                changeLoginWay.text = "手机号快捷登录"
            } else {
                phone.visibility = VISIBLE
                usernmae.visibility = INVISIBLE
                code.visibility = VISIBLE
                password.visibility = INVISIBLE
                getCode.visibility = VISIBLE
                changeLoginWay.text = "用户名密码登录"
            }
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