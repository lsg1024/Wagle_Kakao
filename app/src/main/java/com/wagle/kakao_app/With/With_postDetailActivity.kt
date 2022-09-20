package com.wagle.kakao_app.With

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingList
import com.wagle.kakao_app.Chatting.ChattingRoom
import com.wagle.kakao_app.DTO.PostDetailDTO
import com.wagle.kakao_app.DTO.WithGuestpParticipateDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.declaration_Activity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Response

class With_postDetailActivity : AppCompatActivity() {

    lateinit var user_key : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_post_deail)

        val with_postDeatil_profile : CircleImageView = findViewById(R.id.wtih_postDetail_profile)
        val with_postDetail_name : TextView = findViewById(R.id.with_postDetail_name)
        val with_postDetail_temperature : TextView = findViewById(R.id.with_postDetail_temperature)
        val with_postDetail_title : TextView = findViewById(R.id.with_postDetail_title)
        val with_postDetail_post : TextView = findViewById(R.id.with_postDetail_post)
        val with_postDetail_limitPeople : TextView = findViewById(R.id.with_postDetail_limitPeople)
        val with_postDetail_btn : Button = findViewById(R.id.with_postDetail_btn)
        val postDetail_back : ImageView = findViewById(R.id.postDetail_back)
        val wifiButton : ImageButton = findViewById(R.id.wifiButton)
        val notificationButton : ImageButton = findViewById(R.id.notificationButton)
        val chattingButton : ImageButton = findViewById(R.id.chattingButton)

        val singo : TextView = findViewById(R.id.singo)

        val call by lazy { RetrofitClass.getInstance() }

        val post_key = intent.getIntExtra("post_key", 1)
        Log.d("ttttttt", "성공 $post_key")

        val XCuser_key = intent.getIntExtra("XCuser_key", 1)



        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }


        postDetail_back.setOnClickListener {
            onBackPressed()
        }

        wifiButton.setOnClickListener {
//            val intent = Intent(this@With_postDetailActivity,)
        }

        notificationButton.setOnClickListener {
            val intent = Intent(this@With_postDetailActivity, AlarmList::class.java)
            startActivity(intent)
        }

        chattingButton.setOnClickListener {
            val intent = Intent(this@With_postDetailActivity, ChattingList::class.java)
            startActivity(intent)
        }

        singo.setOnClickListener {
            val intent = Intent(this@With_postDetailActivity, declaration_Activity::class.java)
            intent.putExtra("post_key", post_key)
            intent.putExtra("XCuser_key", XCuser_key)
            startActivity(intent)
        }


        call?.get_postdetail(post_key, user_key)?.enqueue(object : retrofit2.Callback<PostDetailDTO>{
            override fun onResponse(
                call: Call<PostDetailDTO>,
                response: Response<PostDetailDTO>
            ) {
                val response : PostDetailDTO? = response.body()
                Log.d("response2", "성공 ${response!!.db_data[0].nickname}" )


                val your_key = response.user_key[0].user_key

                val url = response.db_data[0].img
                val placeholderImg = R.drawable.indicator_unselected

                val user_keys = response.user_keys

                for (i in user_keys){
                    if (user_key.toInt() == i.user_key){
                        with_postDetail_btn.setOnClickListener{
                            Toast.makeText(this@With_postDetailActivity, "이미 참여한 동행입니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                Glide.with(this@With_postDetailActivity)
                    .load(url)                      // 불러올 이미지 url
                    .placeholder(placeholderImg)    // 이미지 로딩 시작하기 전 이미지
                    .error(placeholderImg)          // 에러 발생시
                    .fallback(placeholderImg)       // 로드일 url이 비어있을 때
                    .into(with_postDeatil_profile)  // 이미지 넣을 뷰

                with_postDeatil_profile.setOnClickListener {
                    val intent = Intent(this@With_postDetailActivity, OtherActivity::class.java)
                    intent.putExtra("your_key", your_key)
                    Log.d("detail", "your_key : ${your_key}")
                    ContextCompat.startActivity(this@With_postDetailActivity, intent, null)
                }

                with_postDetail_name.text = response.db_data[0].nickname
                with_postDetail_title.text = response.db_data[0].title
                with_postDetail_post.text = response.db_data[0].des
                with_postDetail_limitPeople.text = response.db_data[0].personnel.toString()


            }
            override fun onFailure(call: Call<PostDetailDTO>, t: Throwable) {
                Log.d("response222", "실패" + t.message.toString())
            }
        })





        // 참여하기 버튼 다이얼로그
        with_postDetail_btn.setOnClickListener {
//                    val dialog = With_CustomDialog(this@With_postDetailActivity)
//                    dialog.showDia()

            call?.get_guestButton(post_key, user_key)?.enqueue(object : retrofit2.Callback<WithGuestpParticipateDTO>{
                override fun onResponse(
                    call: Call<WithGuestpParticipateDTO>,
                    response: Response<WithGuestpParticipateDTO>
                ) {
                    if(response.isSuccessful){

                        val response: WithGuestpParticipateDTO? = response.body()
                        Log.d("gggg", "성공 ${response!!.db_data}")

                        // 받아서 저장해둘 값 room_key, post_key, title, type

                        val roomKey = response!!.db_data.room_key
                        val postKey = response!!.db_data.post_key
                        val title = response!!.db_data.title
                        val type = response!!.db_data.type


                        val intent = Intent(this@With_postDetailActivity, ChattingRoom::class.java)
                        intent.putExtra("roomKey", roomKey)
                        intent.putExtra("postKey", postKey)
                        intent.putExtra("title", title)
                        intent.putExtra("type", type)
                        startActivity(intent)


                    }else {
                        Log.d("gggg", "실패")
                    }
                }

                override fun onFailure(call: Call<WithGuestpParticipateDTO>, t: Throwable) {
                    Log.d("gggg", "응답 에러")
                }

            })


        }
    }
}