package com.example.android_round5.ui.admingood

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round5.AppService
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.adapter.AdminItemAdapter
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.databinding.FragmentAdminGoodApprovedBinding
import com.example.android_round5.entity.HomeData
import com.example.android_round5.entity.HomeList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminGoodApprovedFragment : Fragment() {
    private var _binding: FragmentAdminGoodApprovedBinding? = null

    private val binding get() = _binding!!
    //TODO
    private lateinit var adminGoodAdapter: AdminItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminGoodApprovedBinding.inflate(inflater, container, false)
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


        appService.AdminGetGood().enqueue(object: Callback<HomeList> {
            override fun onResponse(call: Call<HomeList>, response: Response<HomeList>) {

                val orders = response.body()?.data as ArrayList<HomeData> // 获取订单数据，这里假设有一个 getOrders() 方法返回订单列表
                Log.d("MEWWW", orders.toString())
                val filteredOrders = orders.filter { it.status == 1 }
                adminGoodAdapter = AdminItemAdapter(filteredOrders,this@AdminGoodApprovedFragment)
                binding.adminRecyclerViewApprovedGoods.adapter = adminGoodAdapter
                val layoutManager = GridLayoutManager(requireContext(), 2)
                binding.adminRecyclerViewApprovedGoods.layoutManager = layoutManager
            }
            override fun onFailure(call: Call<HomeList>, t: Throwable) {
                t.printStackTrace()
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
