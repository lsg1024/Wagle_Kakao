package com.wagle.kakao_app.Alarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.alarm_db_data
import com.wagle.kakao_app.R


class AlarmListAdapterViewHolder(v : View) : RecyclerView.ViewHolder(v){

    var alarm_profile : ImageView = itemView.findViewById(R.id.alarm_profile)
    var alarm_peopleName : TextView = itemView.findViewById(R.id.alarm_peopleName)
    //    var alarm_des : TextView = itemView.findViewById(R.id.alarm_des)
    var alarm_time : TextView = itemView.findViewById(R.id.alarm_time)
}

class AlarmListAdapter(private val AlarmDataList:ArrayList<alarm_db_data>):
    RecyclerView.Adapter<AlarmListAdapterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListAdapterViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmListAdapterViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AlarmListAdapterViewHolder, position: Int) {
        Glide.with(holder.alarm_profile.context)
            .load(AlarmDataList[position].img)
            .into(holder.alarm_profile)

        holder.alarm_peopleName.setText(AlarmDataList[position].msg)
        holder.alarm_time.setText(AlarmDataList[position].time)



    }

    override fun getItemCount(): Int {
        return AlarmDataList.size
    }

}