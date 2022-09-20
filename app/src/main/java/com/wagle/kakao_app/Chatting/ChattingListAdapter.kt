package com.wagle.kakao_app.Chatting

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.chat_list
import com.wagle.kakao_app.R

class ChattingListViewHolder(v : View) : RecyclerView.ViewHolder(v){

    var chatList_profile : ImageView = itemView.findViewById(R.id.chatList_profile)
    var chatList_name : TextView = itemView.findViewById(R.id.chatList_name)
    var chatList_msg : TextView = itemView.findViewById(R.id.chatList_msg)

}

class ChattingListAdapter(private val ChattingDataList : ArrayList<chat_list>):RecyclerView.Adapter<ChattingListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingListViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ChattingListViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ChattingListViewHolder, position: Int) {

        Glide.with(holder.chatList_profile.context)
            .load(ChattingDataList[position].img)
            .into(holder.chatList_profile)

        holder.chatList_name.setText(ChattingDataList[position].title)
        holder.chatList_msg.setText(ChattingDataList[position].nickname)


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ChattingRoom::class.java)
            // 채팅 리스트에서 채팅 room에 들어갈 때 필요한 정보들 보내줌
            intent.putExtra("room_key", ChattingDataList[position].room_key)
            intent.putExtra("post_key", ChattingDataList[position].post_key)
            intent.putExtra("title", ChattingDataList[position].title)
            intent.putExtra("type", ChattingDataList[position].type)
            ActivityCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int {
        return ChattingDataList.size
    }
}