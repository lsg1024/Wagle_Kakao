package com.wagle.kakao_app.Chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.R
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChattingRoomAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val datas = ArrayList<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.item_with_list, parent, false)
//        return ChattingRoomViewHolder(cellForRow)
        val view : View?

        return when(viewType) {
            Left_type -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_your_chat, parent, false)
                LeftViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_chat, parent, false)
                RightViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int = datas.size


    fun addItem(item: ChatModel){
        datas.add(item)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return datas[position].type
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


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            1 -> {
                (holder as LeftViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as RightViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val your_name : TextView = view.findViewById(R.id.your_name)
        private val your_profile : CircleImageView = view.findViewById(R.id.your_profile)
        private val your_text : TextView = view.findViewById(R.id.your_Text)
        private val your_Time : TextView = view.findViewById(R.id.your_Time)

        fun bind(item : ChatModel){

            your_name.text = item.nickname

            Glide.with(itemView)
                .load(item.img)
                .into(your_profile)

            your_text.text = item.msg


            var difference = intervalBetweenDateText(item.date.toString())

            your_Time.text = difference

        }

    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val my_chat : TextView = view.findViewById(R.id.my_chat)
        private val my_time : TextView = view.findViewById(R.id.my_time)

        fun bind(item : ChatModel){
            my_chat.text = item.msg

            var difference = intervalBetweenDateText(item.date.toString())
            my_time.text = difference
        }

    }

}