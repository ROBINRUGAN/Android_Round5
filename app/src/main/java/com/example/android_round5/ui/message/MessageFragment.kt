package com.example.android_round5.ui.message

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round5.AppService
import com.example.android_round5.R
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.adapter.MessageItemAdapter
import com.example.android_round5.databinding.FragmentMessageBinding
import com.example.android_round5.entity.ChatList
import com.example.android_round5.entity.ChatListData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMessageBinding.inflate(inflater, container, false)
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
        appService.GetChatList().enqueue(object : retrofit2.Callback<ChatList> {
            override fun onFailure(call: retrofit2.Call<ChatList>, t: Throwable) {
                Log.d("GetChatList", "onFailure: $t")
            }

            override fun onResponse(call: retrofit2.Call<ChatList>, response: retrofit2.Response<ChatList>) {
                val chatList = response.body()
                if (chatList != null) {
                    Log.d("GetChatList", "onResponse: $chatList")
                    val chatListData = chatList.data
                    if (chatListData != null) {
                        Log.d("GetChatList", "onResponse: $chatListData")
                        val layoutManager = LinearLayoutManager(context)
                        binding.messageRecyclerview.layoutManager = layoutManager
                        val adapter = MessageItemAdapter(chatListData, this@MessageFragment)
                        binding.messageRecyclerview.adapter = adapter
                    }
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