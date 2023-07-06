package com.example.android_round5.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_round5.DetailActivity
import com.example.android_round5.ImageActivity
import com.example.android_round5.R

class ImageAdapter(private val images: List<String>, private val detailActivity: DetailActivity) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        // 在这里设置图片到 ImageView
        // holder.imageView.setImageResource(R.drawable.placeholder)
         //使用图片加载库加载图片，如 Glide 或 Picasso
         Glide.with(holder.itemView).load(image).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val imageUrl = image // 替换为你实际的图片 URL
            val intent = Intent(detailActivity, ImageActivity::class.java)
            intent.putExtra(ImageActivity.EXTRA_IMAGE_URL, imageUrl)
            detailActivity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
