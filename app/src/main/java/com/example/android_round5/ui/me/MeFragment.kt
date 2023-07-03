package com.example.android_round5.ui.me

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.android_round5.AppService
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.databinding.FragmentMeBinding
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val meViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        //******************************************************************************************
        val sharedPreferences = context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
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
        appService.GetUserInfo().enqueue(object : retrofit2.Callback<com.example.android_round5.entity.UserInfo> {
            override fun onFailure(call: retrofit2.Call<com.example.android_round5.entity.UserInfo>, t: Throwable) {
                println("请求失败")
            }

            override fun onResponse(
                call: retrofit2.Call<com.example.android_round5.entity.UserInfo>,
                response: retrofit2.Response<com.example.android_round5.entity.UserInfo>
            ) {
                val userInfo = response.body()
                if (userInfo != null) {
                   binding.meUsername.text = userInfo.user.username
                    binding.meNickname.text = userInfo.user.nickname
                    Glide.with(this@MeFragment)
                        .load(userInfo.user.profile_photo)
                        .into(binding.meAvatar)
                }
            }
        })

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}