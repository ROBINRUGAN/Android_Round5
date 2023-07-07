package com.example.android_round5.adapter

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_round5.R
import com.example.android_round5.entity.MessageListData
import com.example.android_round5.ui.me.MeFragment

class ChatAdapter(private val messages: List<MessageListData>,private val activity: Activity) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //获取当前帐号id
        private val messageTextView: TextView = itemView.findViewById(R.id.my_send_message)
        val sharedPreferences = activity.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("id", null)
        private val my_send_message_layout: LinearLayout = itemView.findViewById(R.id.my_send_message_layout)
        fun bind(message: MessageListData) {
            messageTextView.text = message.message

            // 设置不同的样式和背景颜色
            if (message.send_id != id) {
                // 卖家发送的消息样式
                messageTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.bg_seller_message))
                messageTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.seller_message_text_color))
                my_send_message_layout.gravity = Gravity.START

            } else {
                // 买家发送的消息样式
                messageTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.bg_buyer_message))
                messageTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.buyer_message_text_color))
                my_send_message_layout.gravity = Gravity.END
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_send_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
