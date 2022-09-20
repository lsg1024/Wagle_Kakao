package com.wagle.kakao_app.MyPage.list

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.wagle.kakao_app.DTO.UserInfoDTO
import com.wagle.kakao_app.DTO.UserInfoResponse
import com.wagle.kakao_app.DTO.tokenDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfile_insert: AppCompatActivity(){

    private val call by lazy { RetrofitClass.getInstance() }

    lateinit var sex_input : RadioGroup
    lateinit var sex_checked : RadioButton

    lateinit var tags_input : EditText
    lateinit var mbti_input : EditText
    lateinit var intro_input : EditText


    lateinit var accessToken : String
    lateinit var user_key : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_insert)

        sex_input = findViewById(R.id.radio_group_sex)
        // sex_input = findViewById(R.id.editText2)
        tags_input= findViewById(R.id.input_tag)
        mbti_input = findViewById(R.id.input_mbti)
        intro_input = findViewById(R.id.input_intro)

        accessToken = intent.getStringExtra("accessToken").toString()

        val user_key = MySharedPreferences.getUserKey(this)
        if (user_key.isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }

        val submit_btn = findViewById<Button>(R.id.submit_btn)
        val chick_buttom : TextView = findViewById(R.id.click_Buttom)
        var click_data = 0

        chick_buttom.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://oceanit.synology.me:8001/admin/privacy")
            click_data++
            startActivity(intent)
        }




        submit_btn.setOnClickListener {
            sex_checked = findViewById<RadioButton>(sex_input.checkedRadioButtonId)

            // 유저 정보 등록1 (캬카오)
            signUp()
        }
    }


    fun signUp () {
        call?.sendToken(accessToken)?.enqueue(object : Callback<tokenDTO>{
            override fun onResponse(call: Call<tokenDTO>, response: Response<tokenDTO>) {
                val response : tokenDTO? = response.body()

                Log.d("test", "성공 ${response?.tokenResult?.get(0)?.user_key}")
                Log.d("test", response.toString())

                user_key = response?.tokenResult?.get(0)?.user_key.toString()

                Log.d("test10", "$user_key")


                MySharedPreferences.setUserKey(this@MyProfile_insert, user_key.toInt())

                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val deviceToken = task.result
                    Log.d("deviceToken", "$deviceToken")

                    // 유저 정보 등록2 (입력 값)
                    myInfo_retrofit(user_key,
                        UserInfoDTO(nickname = "널",
                            sex = sex_checked.text.toString(),
                            tag = tags_input.text.toString(),
                            mbti = mbti_input.text.toString(),
                            intro = intro_input.text.toString(),
                            token = deviceToken
                        )
                    )
                })




            }

            override fun onFailure(call: Call<tokenDTO>, t: Throwable) {
                Log.e("test", t.message.toString())
            }

        })
    }

    fun myInfo_retrofit (user : String, UserInfoDTO: UserInfoDTO) {

        call?.PostUserInfo(user, UserInfoDTO)?.enqueue(object : Callback<UserInfoResponse> {

            override fun onResponse(
                call: Call<UserInfoResponse>, response: Response<UserInfoResponse>,
            ) {
                if (response.isSuccessful) {

                    val response : UserInfoResponse? = response.body()
                    Log.d("test3", "${response?.result}")

                    Toast.makeText(this@MyProfile_insert, "메인페이지로 이동합니다.", Toast.LENGTH_SHORT).show()

                    // 앱 종료 후 재시작
                    val packageManager = (this@MyProfile_insert).getPackageManager()
                    val intent = packageManager.getLaunchIntentForPackage((this@MyProfile_insert).getPackageName())
                    val componentName = intent!!.component
                    val mainIntent = Intent.makeRestartActivityTask(componentName)
                    (this@MyProfile_insert).startActivity(mainIntent)
                    Runtime.getRuntime().exit(0)

                } else {
                    Log.d("test3", "오류")
                }
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d("test3", "${t.message.toString()}")
            }
        })
    }
}
