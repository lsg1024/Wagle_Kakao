package com.wagle.kakao_app.With

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.MyProfileDTO
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherActivity : AppCompatActivity() {

    val call by lazy { RetrofitClass.getInstance() }


    lateinit var other_backButton : ImageButton
    lateinit var profile_img : CircleImageView
    lateinit var profile_nickname : TextView
    lateinit var profile_mbti : TextView
    lateinit var follow : Button
    lateinit var profile_intro : TextView
    lateinit var profile_tag1 : TextView
    lateinit var profile_tag2 : TextView
    lateinit var profile_tag3 : TextView
    lateinit var profile_tag4 : TextView
    lateinit var seekBar : SeekBar
    lateinit var textView7 : TextView   // 동행온도
    lateinit var dacoImage1 : ImageView
    lateinit var dacoImage2 : ImageView
    lateinit var dacoImage3 : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        other_backButton = findViewById(R.id.other_backButton)
        profile_img = findViewById(R.id.profile_img)
        profile_nickname = findViewById(R.id.profile_nickname)
        profile_mbti = findViewById(R.id.profile_mbti)
        follow = findViewById(R.id.follow)
        profile_intro = findViewById(R.id.profile_intro)
        profile_tag1 = findViewById(R.id.profile_tag1)
        profile_tag2 = findViewById(R.id.profile_tag2)
        profile_tag3 = findViewById(R.id.profile_tag3)
        profile_tag4 = findViewById(R.id.profile_tag4)
        dacoImage1 = findViewById(R.id.dacoImage1)
        dacoImage2 = findViewById(R.id.dacoImage2)
        dacoImage3 = findViewById(R.id.dacoImage3)


        profile_tag1?.text = " "
        profile_tag2?.text = " "
        profile_tag3?.text = " "
        profile_tag4?.text = " "


        val your_key = intent.getIntExtra("your_key", 1).toString()
        Log.d("other11", "your_key : ${your_key}")

        val with_user_key = intent.getIntExtra("with_user_key", 0).toString()
        Log.d("other11", "with : ${with_user_key}")


        other_backButton.setOnClickListener {
            onBackPressed()
        }


        follow.setOnClickListener {

        }

        // 이름, 프로필, 소개, 태그
        if (with_user_key == "0"){
            GetMyProfile_rtf(your_key)
        } else if (your_key == "1"){
            GetMyProfile_rtf(with_user_key)
        }





        // 동행온도


        // 훈장




    }
    fun GetMyProfile_rtf(user_key : String){
        call?.GetMyProfile(user_key)?.enqueue(object : Callback<MyProfileDTO> {
            override fun onResponse(
                call: Call<MyProfileDTO>, response: Response<MyProfileDTO>
            ) {
                if(response.isSuccessful){
//                    val result : MyProfileDTO?= response.body()

                    val url = response.body()?.MyProfile!![0].img
                    val placeholderImg = R.drawable.indicator_unselected

                    Glide.with(this@OtherActivity)
                        .load(url)                      // 불러올 이미지 url
                        .placeholder(placeholderImg)    // 이미지 로딩 시작하기 전 이미지
                        .error(placeholderImg)          // 에러 발생시
                        .fallback(placeholderImg)       // 로드일 url이 비어있을 때
                        .into(profile_img)  // 이미지 넣을 뷰


                    profile_nickname.text = response.body()?.MyProfile!![0].nickname
                    profile_mbti.text = response.body()?.MyProfile!![0].mbti
                    profile_intro.text = response.body()?.MyProfile!![0].intro

                    Log.d("other", "이름 : ${profile_nickname}")


                    // 태그
                    var tags = response.body()?.MyProfile!![0].tag.split(" ")

                    for(i in tags.indices){
                        when (i) {
                            0 -> {
                                profile_tag1.text = tags[i]
                            }
                            1 -> {
                                profile_tag2.text = tags[i]
                            }
                            2 -> {
                                profile_tag3.text = tags[i]
                            }
                            3 -> {
                                profile_tag4.text = tags[i]
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MyProfileDTO>, t: Throwable) {
                Log.d("your_key", "응답 에러러")
            }

        })
    }
}
