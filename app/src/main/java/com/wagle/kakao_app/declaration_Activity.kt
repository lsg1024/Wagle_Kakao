package com.wagle.kakao_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.wagle.kakao_app.DTO.reportDTO
import com.wagle.kakao_app.GuestBook.SearchPackage.ImageClick_Actitivty
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.With.ComingsoonListActivity
import com.wagle.kakao_app.With.RealtimeList
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class declaration_Activity : AppCompatActivity() {

    private val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_declaration)

        val report_comment : Button = findViewById(R.id.report_comment)
        val user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        // 방명록
        val gb_keydata = intent.getIntExtra("gb_key", -1)
        val Xuser_key = intent.getIntExtra("Xuser_key", -1)

        // 동행
        val post_key = intent.getIntExtra("post_key", -1)
        val XCuser_key = intent.getIntExtra("XCuser_key", -1)

        Log.d("report", "${gb_keydata} ${Xuser_key}")
        Log.d("W_report", "${post_key}")
        Log.d("W_report", "${XCuser_key}")

        report_comment.setOnClickListener {


            if (gb_keydata != -1 && Xuser_key != -1) {
                call?.Get_guestBookReport(user_key, Xuser_key, gb_keydata, des = "실험")
                    ?.enqueue(object : retrofit2.Callback<reportDTO> {
                        override fun onResponse(
                            call: Call<reportDTO>,
                            response: Response<reportDTO>
                        ) {

                            Toast.makeText(
                                this@declaration_Activity,
                                "신고접수가 되엇습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                            //                    val intent = Intent(this@declaration_Activity, ImageClick_Actitivty::class.java)
                            //                    startActivity(intent)
                            finish()
                        }

                        override fun onFailure(call: Call<reportDTO>, t: Throwable) {

                        }

                    })
            }

            if (post_key != -1 && XCuser_key != -1) {
                call?.Get_AccompanyReport(user_key, XCuser_key, post_key, des = "동행")?.enqueue(object : retrofit2.Callback<reportDTO> {
                    override fun onResponse(call: Call<reportDTO>, response: Response<reportDTO>) {


                        Toast.makeText(this@declaration_Activity, "신고접수가 되엇습니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@declaration_Activity, RealtimeList::class.java)
                        startActivity(intent)
                    }


                    override fun onFailure(call: Call<reportDTO>, t: Throwable) {

                    }

                })

            }
        }



    }
}