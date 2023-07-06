package com.example.android_round5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        // 获取传递过来的图片 URL
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)

        // 使用 Glide 加载图片到 ImageView
        Glide.with(this)
            .load(imageUrl)
            .into(bigImageShow)
    }
}
