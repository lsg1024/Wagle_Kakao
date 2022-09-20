package com.wagle.kakao_app.Pair

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.PairListDTO
import com.wagle.kakao_app.DTO.pair_list
import com.wagle.kakao_app.R
import de.hdodenhof.circleimageview.CircleImageView

class PairListViewHolder(v : View) : RecyclerView.ViewHolder(v){

    val pair_profile : CircleImageView = itemView.findViewById(R.id.pair_guestProfile)
    val pair_users : TextView = itemView.findViewById(R.id.pair_users)
    val host_frame : ImageView = itemView.findViewById(R.id.host_frame)
}

class PairListAdapter(private val pairListDataList : ArrayList<pair_list>) : RecyclerView.Adapter<PairListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairListViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_pair_list, parent, false)
        return PairListViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: PairListViewHolder, position: Int) {

        holder.pair_users.text = pairListDataList[position].nickname

        if (position != 0){
            holder.host_frame.visibility = View.INVISIBLE
        }

        val connect = pairListDataList[position].connect

        if (connect == 0) {
            holder.pair_profile.setImageResource(R.drawable.wagle_icon)
            holder.pair_users.setTextColor(Color.GRAY)


        } else{
            Glide.with(holder.pair_profile.context)
                .load(pairListDataList[position].img)
                .into(holder.pair_profile)
        }

    }

    override fun getItemCount(): Int {
        return pairListDataList.size
    }
}