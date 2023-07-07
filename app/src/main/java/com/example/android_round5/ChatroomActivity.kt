package com.example.android_round5

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_round5.adapter.ChatAdapter
import com.example.android_round5.adapter.HomeItemAdapter
import com.example.android_round5.entity.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_chatroom.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatroomActivity : AppCompatActivity() {
    private var id = ""
    private var targetId = ""
    private var chatHistory = ArrayList<MessageListData>()
    private lateinit var chatHistoryAdapter: ChatAdapter
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        targetId = intent.getStringExtra("messageItem.id").toString()
        seller_name.text = intent.getStringExtra("messageItem.nickname")
        Glide.with(this).load(intent.getStringExtra("messageItem.profile_photo"))
            .into(seller_avatar_image)
        //******************************************************************************************
        sharedPreferences = this.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
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
        val appService = retrofit.create(AppService::class.java)
        //******************************************************************************************
        appService.GetChatHistory(intent.getStringExtra("messageItem.id")!!)
            .enqueue(object : retrofit2.Callback<MessageList> {
                override fun onFailure(call: retrofit2.Call<MessageList>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: retrofit2.Call<MessageList>,
                    response: retrofit2.Response<MessageList>
                ) {
//                val chatHistory = response.body()
                    chatHistory = response.body()?.data as ArrayList<MessageListData>
                    chatHistoryAdapter = ChatAdapter(chatHistory, this@ChatroomActivity)
                    chatroomRecyclerView.adapter = chatHistoryAdapter
                    chatroomRecyclerView.layoutManager = LinearLayoutManager(this@ChatroomActivity)
                    chatroomRecyclerView.scrollToPosition(chatHistoryAdapter.itemCount - 1)
                    EventBus.getDefault().register(this@ChatroomActivity)

                }
            })
        sendMessageButton.setOnClickListener {
            EventBus.getDefault().post(SendData(newMessage.text.toString(), targetId, 0))
            chatHistory.add(
                MessageListData(
                    newMessage.text.toString(),
                    "1231234123421",
                    targetId,
                    id,
                    "12:12:12",
                    0
                )
            )
            chatHistoryAdapter.notifyDataSetChanged()
            newMessage.text.clear()
            chatroomRecyclerView.scrollToPosition(chatHistoryAdapter.itemCount - 1)
        }
        newMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onMessageReceipt(event: MessageReceipt) {
        Log.d("MEWWW", "你好")
        Snackbar.make(chatroomRecyclerView, "接收到来自${event.send_id}的消息", Snackbar.LENGTH_SHORT)
            .show()

        if ((event.send_id == id && event.receive_id == targetId) || (event.send_id == targetId && event.receive_id == id)) {
            //这是我发的消息 对方收到
            Log.d("MEWWW", "id=${id}")
            Log.d("MEWWW", "targetId=${targetId}")


            val messageListData = MessageListData(
                event.message,
                event.message_id,
                event.receive_id,
                event.send_id,
                event.send_time,
                event.type
            )
            Log.d("MEWWW", "onMessageReceipt: ${chatHistory}")
            chatHistory.add(messageListData)

            chatHistoryAdapter.notifyDataSetChanged()
            chatroomRecyclerView.scrollToPosition(chatHistoryAdapter.itemCount - 1)
        }
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
