package com.example.android_round5

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_round5.entity.Login
import jsonOf
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_code
import kotlinx.android.synthetic.main.activity_login.login_getCode
import kotlinx.android.synthetic.main.activity_login.login_password
import kotlinx.android.synthetic.main.activity_login.login_phone
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context


class LoginActivity : AppCompatActivity() {
    private var mTimeButton: Button? = null
    private var time: TimeCount? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = this@LoginActivity.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val httpClient = OkHttpClient.Builder()
        if (token!=null)
        {
            httpClient.addInterceptor(TokenInterceptor(token))
        }


        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        /**
         * 这部分用来监听验证码的倒计时，并设置时间
         */
        mTimeButton = login_getCode
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


            val map = jsonOf(
                "username" to login_username.text.toString(),
                "password" to login_password.text.toString()
            )
            if(map!=null)
            {
                appService.LoginByUsername(map).enqueue(object : Callback<Login>
                {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        val loginData = response.body()
                        if (loginData != null) {
                            Log.d("MEWWW", loginData.message.toString())
                        }
                        if(loginData!=null)
                        {
                            // 存储令牌
                            val sharedPreferences = this@LoginActivity.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("token", loginData.token)
                            editor.apply()
                            Toast.makeText(this@LoginActivity,loginData.message,Toast.LENGTH_SHORT)

                            Toast.makeText(this@LoginActivity, "欢迎进入MewStore!!ヾ(≧▽≦*)o", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity,MainActivity::class.java)
                            this@LoginActivity.startActivity(
                                intent,ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity).toBundle()
                            )
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

            }

        }

        /**
         * 改变登录方式
         */
        changeLoginWay.setOnClickListener {
            if (changeLoginWay.text.equals("用户名密码登录")) {
                login_phone.visibility = INVISIBLE
                login_username.visibility = VISIBLE
                login_code.visibility = INVISIBLE
                login_password.visibility = VISIBLE
                login_getCode.visibility = INVISIBLE
                changeLoginWay.text = "手机号快捷登录"
            } else {
                login_phone.visibility = VISIBLE
                login_username.visibility = INVISIBLE
                login_code.visibility = VISIBLE
                login_password.visibility = INVISIBLE
                login_getCode.visibility = VISIBLE
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

}