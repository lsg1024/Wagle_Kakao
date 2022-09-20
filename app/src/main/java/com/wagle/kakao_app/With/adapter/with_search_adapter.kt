package com.wagle.kakao_app.With.adapter

import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.WithResponse
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.OtherActivity

class with_Holder(v : View) : RecyclerView.ViewHolder(v){
    var imageView : ImageView = v.findViewById(R.id.with_profile)
    var name : TextView = v.findViewById(R.id.with_name)
}

class with_search_adapter : RecyclerView.Adapter<with_Holder>(){

    private val datalist = mutableListOf<WithResponse>()

    fun addPosts(posts: List<WithResponse>) {
        this.datalist.apply {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        this.datalist.apply {
            clear()
        }
    }
    fun refreshData(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): with_Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.with_search_item, parent, false)
        return with_Holder(itemView)
    }

    override fun onBindViewHolder(holder: with_Holder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(datalist[position].img)
            .into(holder.imageView)

        holder.name.text = datalist[position].nickname

        holder.itemView.setOnClickListener {
            val user_key = datalist[position].user_key

            val intent = Intent(holder.itemView.context, OtherActivity::class.java)
            intent.putExtra("with_user_key", user_key)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }


}