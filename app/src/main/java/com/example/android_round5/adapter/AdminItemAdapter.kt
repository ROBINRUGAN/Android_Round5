package com.example.android_round5.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_round5.AppService
import com.example.android_round5.DetailActivity
import com.example.android_round5.R
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.entity.BidOrderData
import com.example.android_round5.entity.DetailAddData
import com.example.android_round5.entity.HomeData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 首页的商品列表适配器
 * @author MEWWW
 */
class AdminItemAdapter(val homeItemList: List<HomeData>, val fragment: Fragment) :
    RecyclerView.Adapter<AdminItemAdapter.ViewHolder>() {
    /**
     * 内部类用来绑定数据
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.home_item_title)
        val image: ImageView = view.findViewById(R.id.home_item_img)
        val price: TextView = view.findViewById(R.id.home_item_price)
        val likes: TextView = view.findViewById(R.id.home_item_likes)
        val avatar: ImageView = view.findViewById(R.id.home_item_avatar)
        val username: TextView = view.findViewById(R.id.home_item_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)
        val viewHolder = ViewHolder(view)
        /**
         * TODO:这个是点击item时能获取到相应下标
         */
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val homeItem = homeItemList[position]

            /**
             * TODO:这个是进入商品详情页的跳转
             */
            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra("homeItem.add_time", homeItem.add_time)
            intent.putExtra("homeItem.content", homeItem.content)
            intent.putExtra("homeItem.game", homeItem.game)
            intent.putExtra("homeItem.id", homeItem.id)
            intent.putExtra("homeItem.picture_url", homeItem.picture_url)
            intent.putExtra("homeItem.price", homeItem.price)
            intent.putExtra("homeItem.seller_id", homeItem.seller_id)
            intent.putExtra("homeItem.seller_nickname", homeItem.seller_nickname)
            intent.putExtra("homeItem.seller_profile_photo", homeItem.seller_profile_photo)
            intent.putExtra("homeItem.status", homeItem.status)
            intent.putExtra("homeItem.title", homeItem.title)
            intent.putExtra("homeItem.view", homeItem.view.toString())
            view.context.startActivity(intent)
        }
        return viewHolder
    }

    /**
     * 这个是用来更新item的数据的
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //******************************************************************************************
        val sharedPreferences = fragment.context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
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
        holder.setIsRecyclable(false)
        val homeItem = homeItemList[position]
        holder.title.text = homeItem.title
        holder.price.text = homeItem.price
        holder.likes.text = homeItem.view.toString()
        holder.username.text = homeItem.seller_nickname

        if(homeItem.status==0){
            holder.itemView.findViewById<LinearLayout>(R.id.admin_good_btns).visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.findViewById<LinearLayout>(R.id.admin_good_btns).visibility = View.GONE
        }
        holder.itemView.findViewById<Button>(R.id.admingood_pass_btn).setOnClickListener {
            javaAppService.AdminPocessGood(homeItem.id,1).enqueue(object : Callback<BidOrderData> {
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
        holder.itemView.findViewById<Button>(R.id.admingood_fail_btn).setOnClickListener {
            javaAppService.AdminPocessGood(homeItem.id,-1).enqueue(object : Callback<BidOrderData> {
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


        /**
         * 这个是网络图片渲染！
         */
        val urlList = homeItem.picture_url.split(",")  // 假设 responseString 是从后端获取到的字符串
        val firstUrl = urlList.firstOrNull()?.trim() ?: ""  // 获取第一个 URL
        Glide.with(this.fragment).load(firstUrl).into(holder.image)
        Glide.with(this.fragment).load(homeItem.seller_profile_photo).into(holder.avatar)

        //Picasso.with(fragment.context).load(project.imageUrl).into(holder.image)
    }

    /**
     * 获取商品数量
     */
    override fun getItemCount() = homeItemList.size
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}