package com.example.android_round5
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android_round5.adapter.SettingsAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_me.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        listView = findViewById(R.id.listView)

        //******************************************************************************************
        val sharedPreferences = this@SettingsActivity!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", null)
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
        logout_button.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.remove("token")
            editor.apply()
            finish()
            val intent = android.content.Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        appService.GetUserInfo().enqueue(object : retrofit2.Callback<com.example.android_round5.entity.UserInfo> {
            override fun onFailure(call: retrofit2.Call<com.example.android_round5.entity.UserInfo>, t: Throwable) {
                println("请求失败")
            }

            override fun onResponse(
                call: retrofit2.Call<com.example.android_round5.entity.UserInfo>,
                response: retrofit2.Response<com.example.android_round5.entity.UserInfo>
            ) {
                Log.d("MEWWW",response.body().toString())
                val userInfo = response.body()
                if (userInfo != null) {
//                    me_username.text = userInfo.user.username
//                    me_nickname.text = userInfo.user.nickname
//                    Glide.with(this@SettingsActivity)
//                        .load(userInfo.user.profile_photo)
//                        .into(me_avatar)
                    val settingItems = listOf(
                        com.example.android_round5.adapter.SettingItem("手机号", userInfo.user.phone_number),
                        if(userInfo.user.id_card==null)
                            com.example.android_round5.adapter.SettingItem("身份证号", "未填写")
                        else
                            com.example.android_round5.adapter.SettingItem("身份证号", userInfo.user.id_card),

                        if (userInfo.user.status==0)
                            com.example.android_round5.adapter.SettingItem("账号类型", "普通用户")
                        else if(userInfo.user.status==1)
                            com.example.android_round5.adapter.SettingItem("账号类型", "黑名单用户")
                        else if(userInfo.user.status==2)
                            com.example.android_round5.adapter.SettingItem("账号类型", "被冻结用户")
                        else if(userInfo.user.status==3)
                            com.example.android_round5.adapter.SettingItem("账号类型", "管理员用户")
                        else
                            com.example.android_round5.adapter.SettingItem("账号类型", userInfo.user.status.toString())
                    )

                    val listView: ListView = findViewById(R.id.listView)
                    val adapter = SettingsAdapter(this@SettingsActivity, settingItems)
                    listView.adapter = adapter
                }
            }
        })


    }
}

data class SettingItem(val title: String, val value: String)
