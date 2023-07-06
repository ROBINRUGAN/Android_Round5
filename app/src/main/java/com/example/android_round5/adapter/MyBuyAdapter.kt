package com.example.android_round5.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_round5.R
import com.example.android_round5.entity.OrderData


class MyBuyAdapter(private val orders: List<OrderData>) :
    RecyclerView.Adapter<MyBuyAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
        if(order.status==2)
        {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text="未付款"
            holder.itemView.findViewById<Button>(R.id.payBillBtn).visibility= View.VISIBLE
        }
        else if(order.status==0)
        {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text="未确认"
        }
        else if(order.status==1)
        {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text="已通过"
        }
        else if(order.status==-1)
        {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text="已拒绝"
        }

    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.mybuy_id)
        private val goodTitleTextView: TextView = itemView.findViewById(R.id.mybuy_good_title)
        private val priceTextView: TextView = itemView.findViewById(R.id.mybuy_price)
        private val generateTimeTextView: TextView = itemView.findViewById(R.id.mybuy_time)
        private val statusTextView: TextView = itemView.findViewById(R.id.mybuy_status)

        fun bind(order: OrderData) {
            idTextView.text = order.id
            goodTitleTextView.text = order.good_title
            priceTextView.text = order.price.toString()
            generateTimeTextView.text = order.generate_time
            statusTextView.text = order.status.toString()
        }
    }
}