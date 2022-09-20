package com.wagle.kakao_app.With.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.PairData

class PairAdapter(private val context: Context) : RecyclerView.Adapter<PairAdapter.ViewHolder>() {

    var datas = mutableListOf<PairData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val user_img: ImageView = itemView.findViewById(R.id.imageView2)
        private val user_name: TextView = itemView.findViewById(R.id.textView19)
        private val user_rating: RatingBar = itemView.findViewById(R.id.ratingBar2)

        fun bind(item: PairData) {
            user_name.text = item.user_name
            user_rating.id = item.rating
            Glide.with(itemView).load(item.user_img).into(user_img)
        }
    }
}