package com.example.android_round5.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android_round5.AppService
import com.example.android_round5.R
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.entity.BidOrderData
import com.example.android_round5.entity.DetailAddData
import com.example.android_round5.entity.OrderData
import com.example.android_round5.ui.mybuy.AllFragment
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MyBuyAdapter(private val orders: List<OrderData>, val fragment: Fragment) :
    RecyclerView.Adapter<MyBuyAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        //******************************************************************************************
        val sharedPreferences =
            fragment.context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
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
        val order = orders[position]
        holder.itemView.findViewById<Button>(R.id.payBillBtn).setOnClickListener {
            // 创建一个 AlertDialog.Builder 对象
            val builder = AlertDialog.Builder(fragment.context!!)

            // 设置对话框的标题和消息
            builder.setTitle("支付订单")
            builder.setMessage("请确认是否支付该笔订单")
            // 设置对话框的按钮和点击事件
            builder.setPositiveButton("确认") { dialog, which ->

                    javaAppService.PayOrder(order.id).enqueue(object :
                        Callback<BidOrderData> {
                        override fun onFailure(call: Call<BidOrderData>, t: Throwable) {
                            t.printStackTrace()
                        }

                        override fun onResponse(
                            call: Call<BidOrderData>,
                            response: Response<BidOrderData>
                        ) {

                            Log.d("MEWWW", response.code().toString() + response.message())
                            /*******************      封装好的错误处理      ******************/
                            /**************************************************************/
                            if (response.body() == null) {
                                val errorConverter: Converter<ResponseBody, BidOrderData> =
                                    javaRetrofit.responseBodyConverter(
                                        BidOrderData::class.java,
                                        arrayOfNulls(0)
                                    )

                                val errorResponse: BidOrderData? =
                                    errorConverter.convert(response.errorBody()!!)
                                val temp = response.errorBody()!!.string()
                                Log.d("MEWWW", temp)
                                if (errorResponse != null) {
                                    Toast.makeText(
                                        fragment.context,
                                        "errorResponse.msg",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                            if (response.body() != null) {
                                Toast.makeText(
                                    fragment.context,
                                    response.body()!!.msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            /**************************************************************/
                        }
                    })
            }
            builder.setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            // 创建并显示 AlertDialog
            val dialog = builder.create()
            dialog.show()
        }
        holder.bind(order)
        if (order.status == 2) {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text = "你还未付款"
            holder.itemView.findViewById<Button>(R.id.payBillBtn).visibility = View.VISIBLE
        } else if (order.status == 0) {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text = "商家未确认"
        } else if (order.status == 1) {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text = "商家已通过"
        } else if (order.status == -1) {
            holder.itemView.findViewById<TextView>(R.id.mybuy_status).text = "商家已拒绝"
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