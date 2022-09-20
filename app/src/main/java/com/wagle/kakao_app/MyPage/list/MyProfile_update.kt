package com.wagle.kakao_app.MyPage.list.MyGuestBook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.wagle.kakao_app.DTO.MyProfileDTO
import com.wagle.kakao_app.DTO.UserInfoDTO
import com.wagle.kakao_app.DTO.UserInfoResponse
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfile_update : AppCompatActivity() {
    private val call by lazy { RetrofitClass.getInstance() }

//    lateinit var sex_input : RadioGroup
//    lateinit var sex_checked : RadioButton

    lateinit var back_btn : TextView

    lateinit var nickname_input : EditText
    lateinit var tags_input : EditText
    lateinit var mbti_input : EditText
    lateinit var intro_input : EditText

    lateinit var user_key : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_update)

        back_btn = findViewById((R.id.back_btn))

        // sex_input = findViewById(R.id.radio_group_sex)
        // sex_input = findViewById(R.id.editText2)
        nickname_input = findViewById(R.id.input_nickname)
        tags_input= findViewById(R.id.input_tag)
        mbti_input = findViewById(R.id.input_mbti)
        intro_input = findViewById(R.id.input_intro)


        user_key = MySharedPreferences.getUserKey(this)

        val submit_btn = findViewById<Button>(R.id.submit_btn)




        if (user_key.isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }

        back_btn.setOnClickListener {
            finish()
        }


        MyProfile()

        submit_btn.setOnClickListener {

            profile_change(user_key, UserInfoDTO(
                nickname = nickname_input.text.toString(),
                sex = "",
                tag = tags_input.text.toString(),
                mbti = mbti_input.text.toString(),
                intro = intro_input.text.toString(),
                token = "널"
            ))

            Toast.makeText(this, "개인정보 수정 완료", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        MyProfile()
    }


    fun profile_change (user : String, UserInfoDTO: UserInfoDTO) {

        call?.PostprofileChange(user, UserInfoDTO)?.enqueue(object : Callback<UserInfoResponse> {

            override fun onResponse(
                call: Call<UserInfoResponse>, response: Response<UserInfoResponse>,
            ) {
                if (response.isSuccessful) {
                    val response : UserInfoResponse? = response.body()
                    Log.d("test3", "${response?.result}")

                } else {
                    Log.d("test3", "오류")
                }
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d("test3", "${t.message.toString()}")
            }
        })
    }


    fun MyProfile(){
        call?.GetMyProfile(user_key)?.enqueue(object : retrofit2.Callback<MyProfileDTO>{
            override fun onResponse(call: Call<MyProfileDTO>, response: Response<MyProfileDTO>) {
                // val response: MyProfileDTO? = response.body()

                Log.d("MyProfileeeee", "${response.body()?.MyProfile}")

                nickname_input.setText(response.body()?.MyProfile!![0].nickname)
                mbti_input.setText(response.body()?.MyProfile!![0].mbti)
                intro_input.setText(response.body()?.MyProfile!![0].intro)
                tags_input.setText(response.body()?.MyProfile!![0].tag)
            }

            override fun onFailure(call: Call<MyProfileDTO>, t: Throwable) {
                Log.e("MyProfileeeee", t.message.toString())
            }

        })
    }

}