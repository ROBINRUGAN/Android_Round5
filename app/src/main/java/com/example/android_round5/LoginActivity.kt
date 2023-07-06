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
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import com.example.android_round5.entity.GetCode
import okhttp3.ResponseBody


class LoginActivity : AppCompatActivity() {
    private var time: TimeCount? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //调取token和retrofit的预处理
        //******************************************************************************************
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
        //******************************************************************************************




        login_getCode.setOnClickListener {
            /**
             * 这部分用来监听验证码的倒计时，并设置时间
             */
            time = TimeCount(60000, 1000)
            val loginGetCodeData = jsonOf(
                "type" to "login",
                "phone_number" to login_phone.text.toString()
            )

            Log.d("MEWWW",login_phone.text.toString())
            Log.d("MEWWW",loginGetCodeData.toString())
            appService.GetCode(loginGetCodeData).enqueue(object : Callback<GetCode>
            {
                override fun onResponse(call: Call<GetCode>, response: Response<GetCode>) {
                    val getCodeData = response.body()
                    Log.d("MEWWW", getCodeData.toString())
                    /*******************      封装好的错误处理      ******************/
                    /**************************************************************/
                    if(getCodeData==null)
                    {
                        val errorConverter: Converter<ResponseBody, GetCode> =
                            retrofit.responseBodyConverter(GetCode::class.java, arrayOfNulls(0))

                        val errorResponse: GetCode? = errorConverter.convert(response.errorBody()!!)
                        if (errorResponse != null) {
                            Toast.makeText(this@LoginActivity, errorResponse.message, Toast.LENGTH_SHORT).show()

                        }
                    }
                    if (getCodeData != null) {
                        Toast.makeText(this@LoginActivity, getCodeData.message, Toast.LENGTH_SHORT).show()
                        time!!.start()
                    }
                    /**************************************************************/
                }
                override fun onFailure(call: Call<GetCode>, t: Throwable) {
                   t.printStackTrace()
                }
            })

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

            val loginByUsernameData = jsonOf(
                "username" to login_username.text.toString(),
                "password" to login_password.text.toString()
            )
            val loginByPhoneData = jsonOf(
                "phone_number" to login_phone.text.toString(),
                "code" to login_code.text.toString()
            )
            //如果用户名和密码不为空，说明是用户名登录
            if(login_username.text?.isNotBlank() == true && login_password.text?.isNotBlank() == true)
            {
                appService.LoginByUsername(loginByUsernameData).enqueue(object : Callback<Login>
                {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        val loginData = response.body()
                        if(loginData==null)
                        {
                            Toast.makeText(this@LoginActivity,"用户名或密码错误！",Toast.LENGTH_SHORT).show()
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
            //如果手机号和验证码不为空，说明是手机号登录
            else if(login_phone.text?.isNotBlank() == true && login_code.text?.isNotBlank() == true)
            {
//                Toast.makeText(this@LoginActivity, "wqehiqhdiwqhd", Toast.LENGTH_SHORT).show()

                appService.LoginByPhone(loginByPhoneData).enqueue(object : Callback<Login>
                {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        val loginData = response.body()
                        if (loginData != null) {
                            Log.d("MEWWW", loginData.message)
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
            else
            {
                Toast.makeText(this@LoginActivity,"输入不能为空！",Toast.LENGTH_SHORT).show()
            }

        }

        /**
         * 改变登录方式
         */
        changeLoginWay.setOnClickListener {
            if (changeLoginWay.text.equals("用户名密码登录")) {
                login_phone.visibility = INVISIBLE
                login_phone.setText(null)
                login_username.visibility = VISIBLE
                login_code.visibility = INVISIBLE
                login_code.setText(null)
                login_password.visibility = VISIBLE
                login_getCode.visibility = INVISIBLE
                changeLoginWay.text = "手机号快捷登录"
            } else {
                login_phone.visibility = VISIBLE
                login_username.visibility = INVISIBLE
                login_username.setText(null)
                login_code.visibility = VISIBLE
                login_password.visibility = INVISIBLE
                login_password.setText(null)
                login_getCode.visibility = VISIBLE
                changeLoginWay.text = "用户名密码登录"
            }
        }


    }

    /**
     * 验证码按钮显示倒计时类，继承自倒计时类
     * @param millisInFuture
     * 总倒计时长 毫秒
     * @param countDownInterval
     * 倒计时间隔
     */
    internal inner class TimeCount
        (millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            login_getCode.isEnabled = false
            login_getCode.text = (millisUntilFinished / 1000).toString() + "秒后可重发"
        }

        override fun onFinish() { // 计时结束
            login_getCode.isEnabled = true
            login_getCode.text = "重新获取"
        }
    }

}