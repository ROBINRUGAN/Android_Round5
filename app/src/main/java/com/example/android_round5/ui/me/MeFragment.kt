package com.example.android_round5.ui.me

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.android_round5.*
import com.example.android_round5.databinding.FragmentMeBinding
import com.example.android_round5.entity.GetCode
import com.example.android_round5.entity.UserInfo
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
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
        appService.GetUserInfo().enqueue(object : Callback<UserInfo>  {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                println("请求失败")
            }

            override fun onResponse(
                call: Call<UserInfo>,
                response: retrofit2.Response<UserInfo>
            ) {
                val userInfo = response.body()
                /**************************************************************/
                if(userInfo==null)
                {
                    val errorConverter: Converter<ResponseBody, UserInfo> =
                        retrofit.responseBodyConverter(UserInfo::class.java, arrayOfNulls(0))

                    val errorResponse: UserInfo? = errorConverter.convert(response.errorBody()!!)
                    if (errorResponse != null) {
                        Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = android.content.Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
                if (userInfo != null) {
                    Toast.makeText(context, userInfo.message, Toast.LENGTH_SHORT).show()
                    binding.meUsername.text = userInfo.user.username
                    binding.meNickname.text = userInfo.user.nickname
                    binding.meMoney.text = userInfo.user.money
                    Glide.with(this@MeFragment)
                        .load(userInfo.user.profile_photo)
                        .into(binding.meAvatar)
                    if (userInfo.user.status == 3)
                    {
                        binding.adminMenu.visibility= View.VISIBLE
                    }
                }
                /**************************************************************/
            }
        })
        binding.meSetting.setOnClickListener{
            val intent = android.content.Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.meFavoriteBtn.setOnClickListener{
            val intent = android.content.Intent(activity, MyFavoriteActivity::class.java)
            startActivity(intent)
        }
        binding.meBuyBtn.setOnClickListener{
            val intent = android.content.Intent(activity, MyBuyActivity::class.java)
            startActivity(intent)
        }
        binding.meSellerBtn.setOnClickListener{
            val intent = android.content.Intent(activity, MySellActivity::class.java)
            startActivity(intent)
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}