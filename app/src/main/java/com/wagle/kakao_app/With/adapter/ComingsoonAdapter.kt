package com.wagle.kakao_app.With.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.closing_data
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.With_postDetailActivity
import com.wagle.kakao_app.With.Wtih_postDetailHost_Activity
import java.text.SimpleDateFormat
import java.util.*


class ComingsoonAdapterViewHolder(v : View) : RecyclerView.ViewHolder(v){

    var realTimeList_profile : ImageView = itemView.findViewById(R.id.realTimeList_profile)
    var withList_title : TextView = itemView.findViewById(R.id.withList_title)
    var withList_hostName : TextView = itemView.findViewById(R.id.withList_hostName)
    var realTimeList_time : TextView = itemView.findViewById(R.id.realTimeList_time)
    var with_count_personnel : TextView = itemView.findViewById(R.id.with_count_personnel)
    var with_people : TextView = itemView.findViewById(R.id.with_personnel)
}

class ComingsoonAdapter(private val user_key : String): RecyclerView.Adapter<ComingsoonAdapterViewHolder>(){

    private val closingDataList = mutableListOf<closing_data>()

    fun addPosts(posts: List<closing_data>) {
        this.closingDataList.apply {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        this.closingDataList.apply {
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



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingsoonAdapterViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_with_list, parent, false)
        return ComingsoonAdapterViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ComingsoonAdapterViewHolder, position: Int) {
        holder.withList_title.setText(closingDataList[position].title)
        holder.withList_hostName.setText(closingDataList[position].nickname)


        Glide.with(holder.realTimeList_profile.context)
            .load(closingDataList[position].img)
            .into(holder.realTimeList_profile)


        var difference = intervalBetweenDateText(closingDataList[position].date_update.toString())

        holder.realTimeList_time.setText(difference)

//        holder.with_declaration.setOnClickListener {
//            val intent = Intent(holder.with_declaration.context, declaration_Activity::class.java)
//            intent.putExtra("post_key", closingDataList[position].post_key)
//            intent.putExtra("XCuser_key", closingDataList[position].user_key)
//            ActivityCompat.startActivity(holder.with_declaration.context, intent, null)
//        }

        holder.itemView.setOnClickListener{

            if(!user_key.isNullOrEmpty()){

                if(user_key.toInt() == closingDataList[position].user_key){
                    val intent = Intent(holder.itemView?.context, Wtih_postDetailHost_Activity::class.java)
                    intent.putExtra("post_key", closingDataList[position].post_key)
                    intent.putExtra("XCuser_key", closingDataList[position].user_key)
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
                else {
                    val intent = Intent(holder.itemView?.context, With_postDetailActivity::class.java)
                    intent.putExtra("post_key", closingDataList[position].post_key)
                    intent.putExtra("XCuser_key", closingDataList[position].user_key)
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            } else {
                Toast.makeText(holder.itemView.context, "로그인이 필요한 서비스입니다", Toast.LENGTH_SHORT).show()
            }

        }

        holder.with_count_personnel.setText(closingDataList[position].count_personnel.toString())
        holder.with_people.setText(closingDataList[position].personnel.toString())
    }

    override fun getItemCount(): Int {
        return closingDataList.size
    }

    private val runnable = Runnable{  }

}