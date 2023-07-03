package com.example.android_round5.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_round5.AppService
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.databinding.FragmentHomeBinding
import com.example.android_round5.entity.HomeData
import com.example.android_round5.entity.HomeList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var homeItemList = ArrayList<HomeData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /**
         * 利用viewModel来处理动态数据
         */
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
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
        appService.GetHomePage().enqueue(object : retrofit2.Callback<HomeList> {
            override fun onResponse(call: Call<HomeList>, response: Response<HomeList>) {
                Log.d("MEWWW", response.toString())
                homeItemList = response.body()?.data as ArrayList<HomeData>
            }

            override fun onFailure(call: Call<HomeList>, t: Throwable) {
                t.printStackTrace()
            }
        })



        val layoutManager = GridLayoutManager(this.context, 2)
        binding.homeRecyclerview.layoutManager = layoutManager
        Log.d("我要输出了？", homeItemList.toString())
        val adapter = HomeItemAdapter(homeItemList!!, this@HomeFragment)
        binding.homeRecyclerview.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}