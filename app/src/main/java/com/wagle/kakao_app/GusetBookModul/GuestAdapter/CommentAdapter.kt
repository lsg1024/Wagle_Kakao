package com.wagle.kakao_app.GusetBookModul.GuestAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.CommentHeart
import com.wagle.kakao_app.DTO.ReadResponse
import com.wagle.kakao_app.DTO.SearchResponse
import com.wagle.kakao_app.DTO.commentHeartDTO
import com.wagle.kakao_app.GusetBookModul.SearchPage.detail_images
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.declaration_Activity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Response

val call by lazy { RetrofitClass.getInstance() }

class commentHolder(v : View) : RecyclerView.ViewHolder(v) {

    var main_addImage: ImageView = v.findViewById(R.id.main_addImage)
    var main_name: TextView = v.findViewById(R.id.main_username)
    var main_comment: TextView = v.findViewById(R.id.main_comment)
    var main_heart: TextView = v.findViewById(R.id.main_heart)
    var main_heart_img : ImageView = v.findViewById(R.id.heart)
    var comment_user_img : CircleImageView = v.findViewById(R.id.main_user_image)
    var comment_declaration : TextView = v.findViewById(R.id.declaration)

}
class CommentAdapter() : RecyclerView.Adapter<commentHolder>() {

    //    var post_gp_key: Int? = null
    private val dataList = ArrayList<ReadResponse>()

    fun addPosts(posts: List<ReadResponse>) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commentHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.main_comment_item, parent, false)
        return commentHolder(itemView)
    }

    override fun onBindViewHolder(holder: commentHolder, position: Int) {

        val url = "http://oceanit.synology.me:8001/public/images/guestBook/"

        holder.main_name.text = dataList[position].nickname
        holder.main_comment.text = dataList[position].comment
        holder.main_heart.text = dataList[position].heart.toString()

        Glide.with(holder.main_addImage.context)
            .load(url + dataList[position].gb_key + "." + dataList[position].img)
            .into(holder.main_addImage)

        Glide.with(holder.comment_user_img.context)
            .load(dataList[position].user_img)
            .into(holder.comment_user_img)

        holder.main_addImage.setOnClickListener {
            val intent = Intent(holder.main_addImage.context, detail_images::class.java)
            intent.putExtra("detail_image", url + dataList[position].gb_key + "." + dataList[position].img)
            ActivityCompat.startActivity(holder.main_addImage.context, intent, null)
        }

        holder.comment_declaration.setOnClickListener {
            val intent = Intent(holder.comment_declaration.context, declaration_Activity::class.java)
            intent.putExtra("gb_key", dataList[position].gb_key)
            intent.putExtra("Xuser_key", dataList[position].user_key)
            ActivityCompat.startActivity(holder.comment_declaration.context, intent, null)
        }

        var result_boolean : Boolean = true
        var user_data = dataList[position].heart_user
        val key_data = dataList[position].user_key

        try {

            val arr = user_data.split(",")

//            Log.d("logotype1", " arr 값 :  $arr")
//            Log.d("logotype1", " key_data 값 :  $key_data")
//            result_boolean = isPresent(arr, key_data)

            if(arr.contains(key_data.toString())){
                result_boolean = true
                Log.d("logotype1", " result_boolean 값 :  $result_boolean")
            } else {
                Log.d("logotype1", "false")
            }

            if (result_boolean) {
                holder.main_heart_img.setImageResource(R.drawable.favorite_small_full)

                holder.main_heart_img.setOnClickListener {
                    if (result_boolean) {
                        holder.main_heart_img.setImageResource(R.drawable.favorite_smell)
                        holder.main_heart.text = (dataList[position].heart - 1).toString()
                        result_boolean = false
                        comment_Heart_Update(key_data.toString(),
                            commentHeartDTO(dataList[position].gb_key))
                        Log.d("logotype", "if 처음 result_boolean == true")
                    } else {
                        Log.d("logotype", "else 처음 result_boolean == false")
                        holder.main_heart.text = (dataList[position].heart).toString()
                        holder.main_heart_img.setImageResource(R.drawable.favorite_small_full)
                        result_boolean = true
                        comment_Heart_Update(key_data.toString(),
                            commentHeartDTO(dataList[position].gb_key))
                    }
                }
            } else {
                holder.main_heart_img.setImageResource(R.drawable.favorite_smell)

                holder.main_heart_img.setOnClickListener {
                    if (result_boolean) {
                        holder.main_heart_img.setImageResource(R.drawable.favorite_smell)
                        holder.main_heart.text = (dataList[position].heart).toString()
                        result_boolean = false
                        comment_Heart_Update(key_data.toString(),
                            commentHeartDTO(dataList[position].gb_key))
                        Log.d("logotype", "if 처음 result_boolean == true")
                    } else {
                        Log.d("logotype", "if 처음 result_boolean == false")
                        holder.main_heart.text = (dataList[position].heart + 1).toString()
                        holder.main_heart_img.setImageResource(R.drawable.favorite_small_full)
                        result_boolean = true
                        comment_Heart_Update(key_data.toString(),
                            commentHeartDTO(dataList[position].gb_key))
                    }
                }
            }
        } catch (e: NullPointerException){

            holder.main_heart_img.setImageResource(R.drawable.favorite_smell)
            result_boolean = false

            holder.main_heart_img.setOnClickListener {
                if (result_boolean) {
                    Log.d("logotype", "null true")
                    holder.main_heart_img.setImageResource(R.drawable.favorite_smell)
                    holder.main_heart.text = (dataList[position].heart).toString()
                    result_boolean = false
                    comment_Heart_Update(key_data.toString(),
                        commentHeartDTO(dataList[position].gb_key))
                } else {
                    Log.d("logotype", "null false")
                    holder.main_heart.text = (dataList[position].heart + 1).toString()
                    holder.main_heart_img.setImageResource(R.drawable.favorite_small_full)
                    result_boolean = true
                    comment_Heart_Update(key_data.toString(),
                        commentHeartDTO(dataList[position].gb_key))
                }
            }
        }
    }



    override fun getItemCount(): Int {
        var size = 0

        if (dataList != null) {
            size = dataList.size
        }
        return size
    }

//    fun UpdateHeart(context: Context){
//        val user_key = MySharedPreferences.getUserKey(context)
//        if (MySharedPreferences.getUserKey(context).isNullOrEmpty()){
//            Log.d("key_data", "key_data 없음")
//        } else {
//            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(context)}")
//        }
//
////        val call by lazy { RetrofitClass.getInstance() }
////
////        call?.GetReadComment(user_key, )
//
//
//
//    }
}

fun <T> isPresent(arr: List<T>, target: T): Boolean {
    return target in arr
}

private fun comment_Heart_Update(user_key : String, commentHeartDTO : commentHeartDTO){

    call?.CommentHeartupdate(user_key, commentHeartDTO)?.enqueue(object : retrofit2.Callback<CommentHeart>{
        override fun onResponse(call: Call<CommentHeart>, response: Response<CommentHeart>) {

            if (response.isSuccessful){
                val data = response.body()
                Log.d("log", "$data")
                Log.d("log", "$user_key")
            }
            else {
                Log.d("log", "안됨")
                Log.d("log", "$user_key")
            }
        }

        override fun onFailure(call: Call<CommentHeart>, t: Throwable) {
            Log.d("logotype", "응답오류 " + t.message.toString())
            Log.d("log", "$user_key")
        }

    })

}