package com.example.android_round5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.android_round5.adapter.ImageAdapter
import com.example.android_round5.entity.BidOrderData
import com.example.android_round5.entity.DetailAddData
import jsonOf
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //调取token和retrofit的预处理
        //******************************************************************************************
        val sharedPreferences = this@DetailActivity.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val httpClient = OkHttpClient.Builder()
        if (token!=null)
        {
            httpClient.addInterceptor(TokenInterceptor(token))
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)

        val javaRetrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val javaAppService = javaRetrofit.create(AppService::class.java)
        //******************************************************************************************

        detailTitle.text=intent.getStringExtra("homeItem.title")
        detailContent.text=intent.getStringExtra("homeItem.content")
        detailPrice.text=intent.getStringExtra("homeItem.price")
        detailNickname.text=intent.getStringExtra("homeItem.seller_nickname")
        Glide.with(this).load(intent.getStringExtra("homeItem.seller_profile_photo")).into(detailAvatar)


        detailLikes.text=intent.getStringExtra("homeItem.view")
        val urlList =
            intent.getStringExtra("homeItem.picture_url")?.split(",")  // 假设 responseString 是从后端获取到的字符串

        val images = urlList // 替换为实际的图片数据
        val adapter = images?.let { ImageAdapter(it,this) }
        detailImage.adapter = adapter
        detail_pay.setOnClickListener{
            // 创建一个 AlertDialog.Builder 对象
            val builder = AlertDialog.Builder(this)

            // 设置对话框的标题和消息
            builder.setTitle("输入出价")
            builder.setMessage("请输入出价金额：")

            // 创建一个 EditText 用于用户输入
            val inputEditText = EditText(this)
            builder.setView(inputEditText)

            // 设置对话框的按钮和点击事件
            builder.setPositiveButton("确认") { dialog, which ->
                val bidAmount = inputEditText.text.toString().toDoubleOrNull()
                if (bidAmount != null) {
//                    val data=jsonOf(
//                        "good_id" to intent.getStringExtra("homeItem.id")
//                    )
                    // 处理用户输入的出价金额
                    // 在这里执行出价逻辑
                    intent.getStringExtra("homeItem.id")?.let { it1 ->
                        javaAppService.BidOrder(it1,bidAmount).enqueue(object : Callback<BidOrderData> {
                            override fun onFailure(call: Call<BidOrderData>, t: Throwable) {
                                t.printStackTrace()
                            }

                            override fun onResponse(call: Call<BidOrderData>, response: Response<BidOrderData>) {

                                Log.d("MEWWW", response.code().toString()+response.message())
                                /*******************      封装好的错误处理      ******************/
                                /**************************************************************/
                                if(response.body()==null) {
                                    val errorConverter: Converter<ResponseBody, BidOrderData> =
                                        javaRetrofit.responseBodyConverter(BidOrderData::class.java, arrayOfNulls(0))

                                    val errorResponse: BidOrderData? = errorConverter.convert(response.errorBody()!!)
                                    if (errorResponse != null) {
                                        Toast.makeText(this@DetailActivity, errorResponse.msg, Toast.LENGTH_SHORT).show()

                                    }
                                }
                                if (response.body() != null) {
                                    Toast.makeText(this@DetailActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                                }
                                /**************************************************************/
                            }
                        })
                    }
                } else {
                    // 输入的出价金额无效，显示错误提示
                    Toast.makeText(this, "请输入有效的出价金额", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            // 创建并显示 AlertDialog
            val dialog = builder.create()
            dialog.show()
        }
        detail_add.setOnClickListener{
            val data=jsonOf(
                "good_id" to intent.getStringExtra("homeItem.id")
            )
            appService.AddFavorite(data).enqueue(object : Callback<DetailAddData> {
                override fun onFailure(call: Call<DetailAddData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<DetailAddData>, response: Response<DetailAddData>) {
                    response.body()
                    /*******************      封装好的错误处理      ******************/
                    /**************************************************************/
                    if(response.body()==null)
                    {
                        val errorConverter: Converter<ResponseBody, DetailAddData> =
                            retrofit.responseBodyConverter(DetailAddData::class.java, arrayOfNulls(0))

                        val errorResponse: DetailAddData? = errorConverter.convert(response.errorBody()!!)
                        if (errorResponse != null) {
                            Toast.makeText(this@DetailActivity, errorResponse.message, Toast.LENGTH_SHORT).show()

                        }
                    }
                    if (response.body() != null) {
                        Toast.makeText(this@DetailActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                    /**************************************************************/
                }
            })
        }
    }
}