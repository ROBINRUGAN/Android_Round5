package com.example.android_round5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.adapter.OtherItemAdapter
import com.example.android_round5.entity.HomeData
import com.example.android_round5.entity.HomeList
import kotlinx.android.synthetic.main.activity_my_favorite.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyFavoriteActivity : AppCompatActivity() {
    private var homeItemList = ArrayList<HomeData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_favorite)
        //******************************************************************************************
        val sharedPreferences = this.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
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
        appService.GetFavoritePage().enqueue(object : retrofit2.Callback<HomeList> {
            override fun onResponse(call: Call<HomeList>, response: Response<HomeList>) {
                Log.d("MEWWW", response.toString())
                homeItemList = response.body()?.data as ArrayList<HomeData>
                val layoutManager = GridLayoutManager(this@MyFavoriteActivity, 2)
                favorite_recyclerview.layoutManager = layoutManager

                val adapter = OtherItemAdapter(homeItemList, this@MyFavoriteActivity)
                favorite_recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<HomeList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}