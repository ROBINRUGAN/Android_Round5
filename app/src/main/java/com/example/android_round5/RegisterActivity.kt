package com.example.android_round5

import android.app.ActivityOptions
import android.content.Context
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_round5.entity.GetCode
import com.example.android_round5.entity.Register
import jsonOf
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        //******************************************************************************************
        val sharedPreferences =
            this@RegisterActivity.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val httpClient = OkHttpClient.Builder()
        if (token != null) {
            httpClient.addInterceptor(TokenInterceptor(token))
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        //******************************************************************************************

        /**
         * 这部分用来监听验证码的倒计时，并设置时间
         */

        register_getCode.setOnClickListener {
            time = TimeCount(60000, 1000)

            val registerGetCodeData = jsonOf(
                "type" to "register",
                "phone_number" to register_phone.text.toString()
            )

            Log.d("MEWWW", register_phone.text.toString())
            Log.d("MEWWW", registerGetCodeData.toString())
            appService.GetCode(registerGetCodeData).enqueue(object : Callback<GetCode> {

                override fun onFailure(call: Call<GetCode>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<GetCode>, response: Response<GetCode>) {
                    var registerCodeData = response.body()
                    if (registerCodeData == null) {
                        val errorConverter: Converter<ResponseBody, GetCode> =
                            retrofit.responseBodyConverter(GetCode::class.java, arrayOfNulls(0))

                        val errorResponse: GetCode? = errorConverter.convert(response.errorBody()!!)
                        if (errorResponse != null) {
                            Toast.makeText(
                                this@RegisterActivity,
                                errorResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    if (registerCodeData != null) {
                        Toast.makeText(
                            this@RegisterActivity,
                            registerCodeData.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        time!!.start()
                    }

                }
            })
        }

        /**
         * 注册成功就直接进入首页，无需重复登录
         */
        register.setOnClickListener {
            val registerData = jsonOf(
                "username" to register_username.text.toString(),
                "password" to register_password.text.toString(),
                "check_password" to register_password.text.toString(),
                "phone_number" to register_phone.text.toString(),
                "code" to register_code.text.toString(),
            )
            appService.Register(registerData).enqueue(object : Callback<Register> {
                override fun onFailure(call: Call<Register>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Register>, response: Response<Register>) {
                    var registerData = response.body()
                    if (registerData == null) {
                        val errorConverter: Converter<ResponseBody, Register> =
                            retrofit.responseBodyConverter(Register::class.java, arrayOfNulls(0))

                        val errorResponse: Register? = errorConverter.convert(response.errorBody()!!)
                        if (errorResponse != null) {
                            Toast.makeText(
                                this@RegisterActivity,
                                errorResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    if (registerData != null) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "注册成功，正在为你跳转到登录页^_^",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        this@RegisterActivity.startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(this@RegisterActivity).toBundle()
                        )
                    }

                }
            })

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
            register_getCode.isEnabled = false
            register_getCode.text = (millisUntilFinished / 1000).toString() + "秒后可重发"
        }

        override fun onFinish() { // 计时结束
            register_getCode.isEnabled = true
            register_getCode.text = "重新获取"
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