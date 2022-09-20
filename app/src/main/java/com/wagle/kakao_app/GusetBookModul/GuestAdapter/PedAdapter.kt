package com.wagle.kakao_app.GuestBook.SearchPackage

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.SearchResponse
import com.wagle.kakao_app.R

class PedHolder(v : View) : RecyclerView.ViewHolder(v) {

    var imageView: ImageView = v.findViewById(R.id.ped_image)
    var name: TextView = v.findViewById(R.id.name_id)
    var comment: TextView = v.findViewById(R.id.comment_text)
    var heart: TextView = v.findViewById(R.id.heart)

}
class PedAdapter() : RecyclerView.Adapter<PedHolder>(){

    private val dataList =  mutableListOf<SearchResponse>()

    fun addPosts(posts: List<SearchResponse>) {
        this.dataList.apply {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        this.dataList.apply {
            clear()
        }
    }
    fun refreshData(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ped_item_view, parent, false)
        return PedHolder(itemView)
    }

    override fun onBindViewHolder(holder: PedHolder, position: Int) {
        val url = "http://oceanit.synology.me:8001/public/images/guestBook/"

        holder.name.text = dataList[position].nickname
        holder.comment.text = dataList[position].comment
        holder.heart.text = dataList[position].heart.toString()


        Glide.with(holder.imageView.context)
            .load(url + dataList[position].gb_key + "." + dataList[position].img)
            .into(holder.imageView)
        Log.d("response5", url + dataList[position].gb_key + "." + dataList[position].img)

        holder.itemView.setOnClickListener {
            val gp_key = dataList[position].gp_key
            Log.d("pedAdapter", "PedAdapter $gp_key")
            val intent = Intent(holder.itemView.context, ImageClick_Actitivty::class.java)
            intent.putExtra("data", gp_key)
            Log.d("pedAdapter", "$intent")
            startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}