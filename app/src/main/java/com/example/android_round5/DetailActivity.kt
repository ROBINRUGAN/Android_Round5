package com.example.android_round5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailTitle.text=intent.getStringExtra("homeItem.title")
        detailContent.text=intent.getStringExtra("homeItem.content")
        detailPrice.text=intent.getStringExtra("homeItem.price")
        detailNickname.text=intent.getStringExtra("homeItem.seller_nickname")
        Glide.with(this).load(intent.getStringExtra("homeItem.seller_profile_photo")).into(detailAvatar)
        detailLikes.text=intent.getStringExtra("homeItem.view")
        val urlList =
            intent.getStringExtra("homeItem.picture_url")?.split(",")  // 假设 responseString 是从后端获取到的字符串
        val firstUrl = urlList?.firstOrNull()?.trim() ?: ""  // 获取第一个 URL
        Glide.with(this).load(firstUrl).into(detailImage)

    }
}