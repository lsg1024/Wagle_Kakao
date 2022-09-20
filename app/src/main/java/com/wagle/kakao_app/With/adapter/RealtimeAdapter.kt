package com.wagle.kakao_app.With.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.real_time_data
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.With_postDetailActivity
import com.wagle.kakao_app.With.Wtih_postDetailHost_Activity
import java.text.SimpleDateFormat
import java.util.*


class RealtimeViewHolder(v : View) : RecyclerView.ViewHolder(v){

    var realTimeList_profile : ImageView = itemView.findViewById(R.id.realTimeList_profile)
    var withList_title : TextView = itemView.findViewById(R.id.withList_title)
    var withList_hostName : TextView = itemView.findViewById(R.id.withList_hostName)
    var realTimeList_time : TextView = itemView.findViewById(R.id.realTimeList_time)
    var with_count_personnel : TextView = itemView.findViewById(R.id.with_count_personnel)
    var with_people : TextView = itemView.findViewById(R.id.with_personnel)
}

class RealtimeAdapter(private val user_key : String) : RecyclerView.Adapter<RealtimeViewHolder>() {

    private val RealtimeDataList = mutableListOf<real_time_data>()

    fun addPosts(posts: List<real_time_data>) {
        this.RealtimeDataList.apply {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        this.RealtimeDataList.apply {
            clear()
        }
    }

    fun refreshData(){
        notifyDataSetChanged()
    }

    fun intervalBetweenDateText(beforeDate : String) : String {
        // 현재 time
        var now = System.currentTimeMillis()
        var nowFormat = Date(now)

        // 서버 time
        val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate)

        val diffSec     = (nowFormat.time - beforeFormat.time) / 1000                                           //초 차이
        val diffMin     = (nowFormat.time - beforeFormat.time) / (60*1000)                                      //분 차이
        val diffHor     = (nowFormat.time - beforeFormat.time) / (60 * 60 * 1000)                               //시 차이
        val diffDays    = diffSec / (24 * 60 * 60)                                                              //일 차이
        val diffMonths  = (nowFormat.year*12 + nowFormat.month) - (beforeFormat.year*12 + beforeFormat.month)   //월 차이
        val diffYears   = nowFormat.year - beforeFormat.year                                                    //연도 차이


        if(diffYears > 0){ return "${diffYears}년 전" }

        if(diffMonths > 0){ return "${diffMonths}개월 전" }

        if (diffDays > 0){ return "${diffDays}일 전" }

        if(diffHor > 0){ return "${diffHor}시간 전" }

        if(diffMin > 0){ return "${diffMin}분 전" }

        if(diffSec > 0){ return "${diffSec}초 전" }

        return ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealtimeViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_with_list, parent, false)
        return RealtimeViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RealtimeViewHolder, position: Int) {
        holder.withList_title.text = RealtimeDataList[position].title
        holder.withList_hostName.text = RealtimeDataList[position].nickname


        Glide.with(holder.realTimeList_profile.context)
            .load(RealtimeDataList[position].img)
            .into(holder.realTimeList_profile)

        var difference = intervalBetweenDateText(RealtimeDataList[position].date_update.toString())

        holder.realTimeList_time.text = difference


        holder.itemView.setOnClickListener {

            if(!user_key.isNullOrEmpty()){

                if(user_key.toInt() == RealtimeDataList[position].user_key){
                    val intent = Intent(holder.itemView.context, Wtih_postDetailHost_Activity::class.java)
                    intent.putExtra("post_key", RealtimeDataList[position].post_key)
                    intent.putExtra("XCuser_key", RealtimeDataList[position].user_key)
                    Log.d("ttttttt", "${RealtimeDataList[position].post_key}")
                    startActivity(holder.itemView.context, intent, null)

                } else {
                    val intent = Intent(holder.itemView.context, With_postDetailActivity::class.java)
                    intent.putExtra("post_key", RealtimeDataList[position].post_key)
                    intent.putExtra("XCuser_key", RealtimeDataList[position].user_key)

                    Log.d("ttttttt", "${RealtimeDataList[position].post_key}")
                    startActivity(holder.itemView.context, intent, null)
                }
            } else {
                Toast.makeText(holder.itemView.context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }

        }
        holder.with_count_personnel.text = RealtimeDataList[position].count_personnel.toString()
        holder.with_people.text = RealtimeDataList[position].personnel.toString()
    }

    override fun getItemCount(): Int {
        return RealtimeDataList.size
    }

    private val runnable = Runnable{  }

}
