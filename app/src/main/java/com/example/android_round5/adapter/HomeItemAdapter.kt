package com.example.android_round5.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_round5.R
import com.example.android_round5.entity.HomeItem

/**
 * 首页的商品列表适配器
 * @author MEWWW
 */
class HomeItemAdapter(val homeItemList: List<HomeItem>, val fragment: Fragment) : RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {
    /**
     * 内部类用来绑定数据
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.home_item_title)
        val image: ImageView = view.findViewById(R.id.home_item_img)
        val price: TextView = view.findViewById(R.id.home_item_price)
        val likes: TextView = view.findViewById(R.id.home_item_likes)
        val avatar: ImageView = view.findViewById(R.id.home_item_avatar)
        val username:TextView = view.findViewById(R.id.home_item_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item,parent,false)
        val viewHolder = ViewHolder(view)
        /**
         * TODO:这个是点击item时能获取到相应下标
         */
//        viewHolder.itemView.setOnClickListener{
//            val position = viewHolder.bindingAdapterPosition
//            val project = projectList[position]

            /**
             * TODO:这个是进入商品详情页的跳转
             */
//            val intent = Intent(view.context,DetailActivity::class.java)
//            intent.putExtra("project.title",project.title)
//            intent.putExtra("project.imageurl",project.imageurl)
//            intent.putExtra("project.content",project.content)
//            intent.putExtra("project.price",project.price)
//            intent.putExtra("project.telephone",project.telephone)
//            intent.putExtra("project.id",project.id)
//            intent.putExtra("user.id",project.userid)
//            view.context.startActivity(intent)
        return viewHolder
    }

    /**
     * 这个是用来更新item的数据的
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val homeItem = homeItemList[position]
        holder.title.text = homeItem.title
        holder.image.setImageResource(homeItem.image)
        holder.price.text = homeItem.price.toString()
        holder.likes.text = homeItem.likes.toString()
        holder.username.text = homeItem.username
        holder.avatar.setImageResource(homeItem.avatar)

        /**
         * 这个是网络图片渲染！
          */
    //Glide.with(this.fragment).load(project.imageurl).into(holder.image)
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