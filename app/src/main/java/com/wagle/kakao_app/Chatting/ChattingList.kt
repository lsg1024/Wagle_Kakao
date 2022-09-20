package com.wagle.kakao_app.Chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.ChatListDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChattingList : AppCompatActivity() {

    lateinit var user_key : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_list)

        val chat_backButton = findViewById<ImageButton>(R.id.chat_backButton)

        chat_backButton.setOnClickListener {
            onBackPressed()
        }


        // 로컬에 있는 user_key 가져옴
        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }



        val call by lazy { RetrofitClass.getInstance() }

        call?.get_chatList(user_key)?.enqueue(object : Callback<ChatListDTO>{
            override fun onResponse(call: Call<ChatListDTO>, response: Response<ChatListDTO>) {

                if (response.isSuccessful){
                    val response : ChatListDTO? = response.body()
                    Log.d("ChatList_response", "성공 ${response?.chat_info!!}")

                    val ChattingDataList = response?.chat_info

                    val chat_list : RecyclerView = findViewById(R.id.chat_list)


                    chat_list.layoutManager = LinearLayoutManager(applicationContext)
                    chat_list.adapter = ChattingListAdapter(ChattingDataList)

                } else {
                    Log.d("ChatList_response", "실패")
                }
            }

            override fun onFailure(call: Call<ChatListDTO>, t: Throwable) {
                Log.d("ChatList_response", "응답오류")
            }

        })

    }
}