package com.wagle.kakao_app.With

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingRoom
import com.wagle.kakao_app.DTO.PostDetailDTO
import com.wagle.kakao_app.DTO.WithEndButtonDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.Pair.Pair_MainActivity
import com.wagle.kakao_app.R
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Response

class Wtih_postDetailHost_Activity : AppCompatActivity() {


    // 필요한 변수
    lateinit var postDetail_back : ImageView
    lateinit var wtih_postDetail_profile : CircleImageView
    lateinit var with_postDetail_name : TextView
    lateinit var with_postDetail_temperature : TextView
    lateinit var with_postDetail_title : TextView
    lateinit var with_postDetail_post : TextView
    lateinit var with_postDetail_limitPeople : TextView
    lateinit var postDetail_edit : Button
    lateinit var postDetail_end : Button

    // retrofit
    val call by lazy { RetrofitClass.getInstance() }

    // 본인 user_key
    lateinit var user_key : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wtih_post_detail_host)





        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }


        // 뒤로가기 버튼
        postDetail_back = findViewById(R.id.postDetail_back)

        wtih_postDetail_profile = findViewById(R.id.wtih_postDetail_profile)
        with_postDetail_name = findViewById(R.id.with_postDetail_name)

//        with_postDetail_temperature

        with_postDetail_title = findViewById(R.id.with_postDetail_title)
        with_postDetail_post = findViewById(R.id.with_postDetail_post)
        with_postDetail_limitPeople = findViewById(R.id.with_postDetail_limitPeople)

        postDetail_edit = findViewById(R.id.postDetail_edit)
        postDetail_end = findViewById(R.id.postDetail_end)




        postDetail_back.setOnClickListener{
            finish()
        }


        val post_key = intent.getIntExtra("post_key", 1)
        Log.d("ttttttt", "성공 $post_key")



        call?.get_postdetail(post_key, user_key)?.enqueue(object : retrofit2.Callback<PostDetailDTO>{
            override fun onResponse(call: Call<PostDetailDTO>, response: Response<PostDetailDTO>) {

                val response : PostDetailDTO? = response.body()
//                Log.d("response", "성공 ${response!!.db_data[0].nickname}")

                if (response!!.db_data.size != 0){
                    val url = response!!.db_data[0].img
                    val placeholderImg = R.drawable.indicator_unselected


                    Glide.with(this@Wtih_postDetailHost_Activity)
                        .load(url)                      // 불러올 이미지 url
                        .placeholder(placeholderImg)    // 이미지 로딩 시작하기 전 이미지
                        .error(placeholderImg)          // 에러 발생시
                        .fallback(placeholderImg)       // 로드일 url이 비어있을 때
                        .into(wtih_postDetail_profile)  // 이미지 넣을 뷰


                    with_postDetail_name.text = response.db_data[0].nickname
                    Log.d("ttttttt", "nickname : ${with_postDetail_name}")
                    with_postDetail_title.text = response.db_data[0].title
                    with_postDetail_post.text = response.db_data[0].des
//                    Log.d("tesdsg", "${response.db_data[0].personnel.toString()}")

                    with_postDetail_limitPeople.text = response.db_data[0].personnel.toString()
                } else {

                }

            }

            override fun onFailure(call: Call<PostDetailDTO>, t: Throwable) {
                Log.d("response222", "실패" + t.message.toString())
            }
        }
        )

        // 수정하기 버튼
        postDetail_edit.setOnClickListener {
            Toast.makeText(this@Wtih_postDetailHost_Activity, "추후 업데이트 예정", Toast.LENGTH_SHORT).show()
        }


        // 마감하기 버튼을 눌렀을 때
        postDetail_end.setOnClickListener {
            call?.post_postDetail_end(post_key, user_key)?.enqueue(object : retrofit2.Callback<WithEndButtonDTO>{
                override fun onResponse(
                    call: Call<WithEndButtonDTO>,
                    response: Response<WithEndButtonDTO>
                ) {
                    val response : WithEndButtonDTO? = response.body()
                    Log.d("Host", "성공 ${response}")
                    val intent = Intent(this@Wtih_postDetailHost_Activity, Pair_MainActivity::class.java)
                    intent.putExtra("host_key", user_key)
                    startActivity(intent)
                }
                override fun onFailure(call: Call<WithEndButtonDTO>, t: Throwable) {
                    Log.d("Host", "응답 에러")
                }

            })

        }

    }
}