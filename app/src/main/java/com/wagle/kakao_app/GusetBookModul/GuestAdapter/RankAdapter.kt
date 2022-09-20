package com.wagle.kakao_app.GuestBook

import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.Responeresult
import com.wagle.kakao_app.GuestBook.SearchPackage.ImageClick_Actitivty
import com.wagle.kakao_app.R

//class Data(val LankingText: String)

class LankHolder(v : View) : RecyclerView.ViewHolder(v){

    var lanktext : TextView = v.findViewById(R.id.lankText)

}

class LankAdapter(val DataList: ArrayList<Responeresult>) : RecyclerView.Adapter<LankHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LankHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lank_item_recycler, parent, false)
        return LankHolder(itemView)
    }

    override fun onBindViewHolder(holder: LankHolder, position: Int) {

            holder.lanktext.text = "${position + 1}." +" "+ DataList[position].place

            if(position == 0){
                holder.lanktext.setTextColor(ContextCompat.getColor(holder.lanktext.context, R.color.rank1))
                holder.lanktext.setTypeface(null, Typeface.BOLD)
            } else if (position == 1){
                holder.lanktext.setTextColor(ContextCompat.getColor(holder.lanktext.context, R.color.rank2))
                holder.lanktext.setTypeface(null, Typeface.BOLD)
            } else if (position == 2){
                holder.lanktext.setTextColor(ContextCompat.getColor(holder.lanktext.context, R.color.rank3))
                holder.lanktext.setTypeface(null, Typeface.BOLD)
            }

            holder.itemView.setOnClickListener {
                val gp_key = DataList[position].gp_key
                Log.d("lankAdapter", "PedAdapter $gp_key")
                val intent = Intent(holder.itemView.context, ImageClick_Actitivty::class.java)
                intent.putExtra("lank_data", gp_key)
                startActivity(holder.itemView.context, intent, null)

            }
    }

    override fun getItemCount(): Int {
        if(DataList.size < 5){
            return  DataList.size
        } else{
            return 5;
        }
    }


}

