package com.wagle.kakao_app.GusetBookModul.SearchPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.R

class detail_images : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_images)

        val image : ImageView = findViewById(R.id.detail_images)
        val load_image = intent.getStringExtra("detail_image")

        Glide.with(this)
            .load(load_image)
            .into(image)
    }
}