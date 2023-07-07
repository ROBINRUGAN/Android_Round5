package com.example.android_round5.ui.message

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round5.AppService
import com.example.android_round5.R
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.adapter.MessageItemAdapter
import com.example.android_round5.databinding.ActivityMainBinding
import com.example.android_round5.databinding.FragmentMessageBinding
import com.example.android_round5.entity.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import jsonOf
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MessageFragment : Fragment() {

    var id = ""
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var socket: Socket
    lateinit var appService: AppService

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
        id = sharedPreferences?.getString("id", null).toString()
        val httpClient = OkHttpClient.Builder()
        if (token != null) {
            httpClient.addInterceptor(TokenInterceptor(token))
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        appService = retrofit.create(AppService::class.java)
        //******************************************************************************************
        EventBus.getDefault().register(this)
        connectSocketIO()
        listenSocketIO()
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

    private fun connectSocketIO() {
         sharedPreferences = context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        id= sharedPreferences?.getString("id", null).toString()
        val token = sharedPreferences?.getString("token", null)
        // 创建 OkHttp 实例
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(token!!))
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build()
        val options = IO.Options()
        options.callFactory = okHttpClient
        // 创建 Socket.IO 连接实例
        val mSocket: Socket = IO.socket("http://api.mewtopia.cn:5000/chat", options)
        socket = mSocket

        // 连接到服务器
        socket.connect()
    }

    private fun listenSocketIO() {
        val gson = Gson()
        // 连接成功的监听器
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("socket", "Connected to server")
        }
        // 添加连接断开的监听器
        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d("socket", "Disconnected from server")
            val jsonMsg = it.joinToString {
                it?.toString() ?: "null"
            }
        }
        //连接错误的监听器
        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            // 连接错误的处理逻辑
            val exception = args[0] as Exception
            Log.e("socket", "连接错误: $exception")
        }
        //message事件的监听器
        socket.on("response") { args ->

            val jsonMsg = args.joinToString {
                it?.toString() ?: "null"
            }
            Log.d("MEWWW", "收到消息: $jsonMsg")
            lateinit var message: MessageReceipt
            if(args.size==2)
            {
                message = gson.fromJson(args[1].toString(), MessageReceipt::class.java)
                EventBus.getDefault().post(message)
            }
            else if(args.size==1)
            {
                message = gson.fromJson(args[0].toString(), MessageReceipt::class.java)
                EventBus.getDefault().post(message)
            }


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageSend(event: SendData) {
        Log.d("zz", "接收到发送消息事件")
        val gson = Gson()
        socket.send(gson.toJson(event).toString())
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onMessageReceipt(event: MessageReceipt) {
        Log.d("zz", event.message)
        //如果是我发的消息
        if (event.send_id != id) {
            Snackbar.make(binding.root, "接收到来自${event.send_id}的消息", Snackbar.LENGTH_SHORT).show()
            val data = jsonOf(

                "message_id" to event.message_id,
                "send_id" to event.send_id
            )
            appService.ReadMessage(data).enqueue(object : Callback<DetailAddData>
            {
                override fun onFailure(call: Call<DetailAddData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<DetailAddData>,
                    response: Response<DetailAddData>
                ) {
                }

            })
        }

    }

    override fun onDestroy() {
        socket.disconnect()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}