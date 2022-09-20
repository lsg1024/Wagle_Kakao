package com.wagle.kakao_app.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.R

// Kotlin Android Tutorial - View Pager2 [Youtube] 참고


class IndicatorViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    var heart : TextView = itemView.findViewById(R.id.main_guestBook_heart)
    var name : TextView =  itemView.findViewById(R.id.main_guestBook_name)
    var address : TextView = itemView.findViewById(R.id.main_guestBook_address)

}


//ResultBanner : 서버에서 불러올 때 사용하는 리스트
class IndicatorAdapter(var main_guestItem:ArrayList<String>) : RecyclerView.Adapter<IndicatorViewHolder>() {

//    var main_guestItem = arrayOf<String>(
//        "aa,bb,cc",
//        "111,222,333",
//        "qqq,www,eee"
//    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {

        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_banner, parent, false)
        return IndicatorViewHolder(cellForRow)

    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        var value = main_guestItem[position].split(",")
        holder.heart.setText(value[0])
        holder.name.setText(value[1])
        holder.address.setText(value[2])
    }


    override fun getItemCount(): Int {
        return main_guestItem.size
    }

}