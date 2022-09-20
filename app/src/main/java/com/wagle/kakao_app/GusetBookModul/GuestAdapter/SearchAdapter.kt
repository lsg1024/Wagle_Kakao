package com.wagle.finaltest.GuestBook.SearchPackage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.SearchResponse
import com.wagle.kakao_app.GuestBook.SearchPackage.ImageClick_Actitivty
import com.wagle.kakao_app.R

class SearchHolder(v : View) : RecyclerView.ViewHolder(v){

    var imageView : ImageView = v.findViewById(R.id.search_image)
    var name : TextView = v.findViewById(R.id.search_name_id)
    var comment : TextView = v.findViewById(R.id.search_comment_text)
    var heart : TextView = v.findViewById(R.id.heart)

}

class SearchAdapter : RecyclerView.Adapter<SearchHolder>(){

    private val DataList = mutableListOf<SearchResponse>()

    fun addPosts(posts: List<SearchResponse>) {
        this.DataList.apply {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        this.DataList.apply {
            clear()
        }
    }
    fun refreshData(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item_recycler, parent, false)
        return SearchHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val url = "http://oceanit.synology.me:8001/public/images/guestBook/"

        holder.name.text = DataList[position].nickname
        holder.comment.text = DataList[position].comment
        holder.heart.text = DataList[position].heart.toString()

        Glide.with(holder.imageView.context)
            .load(url + DataList[position].gb_key + "." + DataList[position].img)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val gp_key = DataList[position].gp_key

            val intent = Intent(holder.itemView.context, ImageClick_Actitivty::class.java)
            intent.putExtra("data", gp_key)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

}