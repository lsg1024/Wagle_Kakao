package com.wagle.kakao_app.Pair

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.PairListDTO
import com.wagle.kakao_app.DTO.guesetBook_report
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.PairCustomDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Pair_MainActivity : AppCompatActivity() {

    lateinit var user_key : String
    lateinit var pairList_adapter : PairListAdapter
    lateinit var pair_backButton: ImageButton
    lateinit var finish_button: Button
    lateinit var users_list : RecyclerView
    var Host_key : Int = 0

    val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pair_main)

        val QR_button = findViewById<ImageButton>(R.id.pair_QRButton)
        pair_backButton = findViewById(R.id.pair_backButton)
        finish_button = findViewById(R.id.finish_trip)
        users_list = findViewById(R.id.users_list)

        user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

// ************************* ***********************큐알 리턴 값 레트로핏 호출 ************************* ************************* //


// ************************* ********************************************************************* ************************* //

        call?.getPairList(user_key)?.enqueue(object : Callback<PairListDTO> {
            override fun onResponse(call: Call<PairListDTO>, response: Response<PairListDTO>) {
                if (response.isSuccessful){

                    val pairResponse : PairListDTO? = response.body()
                    Log.d("pairList", "성공 ${pairResponse?.db_data}")


                    // 호스트 키 받기
                    Host_key = pairResponse!!.host_key
                    Log.d("pairList", "host" + "$Host_key")

                    // post_key 방키
                    val post_key = pairResponse!!.post_key

                    QR_button.setOnClickListener {
                        val intent = Intent(this@Pair_MainActivity, QR_Activity::class.java)
                        if (user_key.toInt() == Host_key){
                            intent.putExtra("host_key", Host_key)
                            intent.putExtra("post_key", post_key)
                            startActivity(intent)
                        } else {
                            intent.putExtra("guest_key", user_key)
                            startActivity(intent)
                        }
                    }





                    val pairListDataList = pairResponse?.db_data!!
                    users_list.layoutManager = LinearLayoutManager(this@Pair_MainActivity, LinearLayoutManager.VERTICAL, false)

                    pairList_adapter = PairListAdapter(pairListDataList)

                    users_list.adapter = pairList_adapter


                } else {
                    Log.d("pairList", "실패")
                }
            }

            override fun onFailure(call: Call<PairListDTO>, t: Throwable) {
                Log.d("pairList", "응답오류 ${t.message.toString()}")
            }

        })

        pair_backButton.setOnClickListener {
            onBackPressed()
        }

        finish_button.setOnClickListener {
            val dialog = PairCustomDialog(this)
            dialog.showDia(this)
        }

    }
}
