package com.example.android_round5.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_round5.DetailActivity
import com.example.android_round5.R
import com.example.android_round5.entity.HomeData

/**
 * 首页的商品列表适配器
 * @author MEWWW
 */
class HomeItemAdapter(val homeItemList: ArrayList<HomeData>, val fragment: Fragment) :
    RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {
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
        holder.setIsRecyclable(false)
        val homeItem = homeItemList[position]
        holder.title.text = homeItem.title
        holder.price.text = homeItem.price
        holder.likes.text = homeItem.view.toString()
        holder.username.text = homeItem.seller_nickname

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