package com.wagle.kakao_app.Alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.AlarmDTO
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)

        val alarm_backButton = findViewById<ImageButton>(R.id.alarm_backButton)

        alarm_backButton.setOnClickListener {
            onBackPressed()
        }


        val call by lazy { RetrofitClass.getInstance() }


        call?.get_alarm(user_key = 7)?.enqueue(object : Callback<AlarmDTO>{
            override fun onResponse(
                call: Call<AlarmDTO>, response: Response<AlarmDTO>) {

                if (response.isSuccessful){

                    val result : AlarmDTO? = response.body()
                    Log.d("alarmresponse", "성공 ${result?.alarm_db_data!!}")

                    val AlarmDataList = result?.alarm_db_data

                    val alarmList = findViewById<RecyclerView>(R.id.alarm_list)

                    alarmList.layoutManager = LinearLayoutManager(applicationContext)

                    alarmList.adapter = AlarmListAdapter(AlarmDataList)

                } else {
                    Log.d("alarmresponse", "실패")
                }
            }

            override fun onFailure(call: Call<AlarmDTO>, t: Throwable) {
                Log.d("alarmresponse", "응답오류" + t.message.toString())
            }

        })
    }
}