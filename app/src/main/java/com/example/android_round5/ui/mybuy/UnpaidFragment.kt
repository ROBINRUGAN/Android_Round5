package com.example.android_round5.ui.mybuy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round5.AppService
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.adapter.MyBuyAdapter
import com.example.android_round5.databinding.FragmentUnpaidBinding
import com.example.android_round5.entity.OrderData
import com.example.android_round5.entity.OrderList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnpaidFragment : Fragment() {
    private var _binding: FragmentUnpaidBinding? = null

    private val binding get() = _binding!!
    private lateinit var mybuyAdapter: MyBuyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnpaidBinding.inflate(inflater, container, false)
        //******************************************************************************************
        val sharedPreferences = context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", null)
        val httpClient = OkHttpClient.Builder()
        if (token != null) {
            httpClient.addInterceptor(TokenInterceptor(token))
        }
        val javaRetrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val javaAppService = javaRetrofit.create(AppService::class.java)
        //******************************************************************************************


        javaAppService.GetMyBuy().enqueue(object: Callback<OrderList> {
            override fun onResponse(call: Call<OrderList>, response: Response<OrderList>) {


                val orders = response.body()?.data as ArrayList<OrderData> // 获取订单数据，这里假设有一个 getOrders() 方法返回订单列表


                val filteredOrders = orders.filter { it.status == 2 }
                mybuyAdapter = MyBuyAdapter(filteredOrders)
                binding.recyclerViewUnpaidOrders.adapter = mybuyAdapter
                binding.recyclerViewUnpaidOrders.layoutManager = LinearLayoutManager(requireContext())
            }
            override fun onFailure(call: Call<OrderList>, t: Throwable) {
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