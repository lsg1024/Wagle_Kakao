package com.wagle.kakao_app.With

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.wagle.kakao_app.DTO.PostResult
import com.wagle.kakao_app.DTO.WithPostCreateDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  newWrite_WithActivity : AppCompatActivity() {

    private lateinit var getresultText: ActivityResultLauncher<Intent>


    // 본인 user_key
    lateinit var user_key : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_write_with)

        Log.d("test", "onCreate")

        val newWrite_title : EditText = findViewById(R.id.newWrite_title)
        val newWrite_mainText : EditText = findViewById(R.id.newWrite_mainText)
        val newWrite_hashTag : EditText = findViewById(R.id.newWrite_hashTag)
        val limit_people_input : EditText = findViewById(R.id.limit_people_input)
        val newWrite_finishBtn : Button = findViewById(R.id.newWrite_finishBtn)


        // user_key를 header에 넣음
        val user_key = MySharedPreferences.getUserKey(this@newWrite_WithActivity)

        if (user_key.isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }

        val chick_buttom : TextView = findViewById(R.id.click_Buttom2)

        chick_buttom.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://oceanit.synology.me:8001/admin/privacy")
            startActivity(intent)
        }

        val call by lazy { RetrofitClass.getInstance() }

        newWrite_finishBtn.setOnClickListener{

            val create = WithPostCreateDTO("${newWrite_title.text}", "${newWrite_mainText.text}", "${newWrite_hashTag.text}","${limit_people_input.text}")

            call?.withPost_create(user_key, create)?.enqueue(object : Callback<PostResult> {
                override fun onResponse(
                    call: Call<PostResult>, response: Response<PostResult>
                ) {
                    if(response.isSuccessful){
                        var data = response.body()
                        Log.d("createTest", "$data")

                        Toast.makeText(this@newWrite_WithActivity, "작성이 완료되었습니다", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@newWrite_WithActivity, Wtih_postDetailHost_Activity::class.java)
                        intent.putExtra("post_key", data!!.post_key)
                        intent.putExtra("user_key", data!!.user_key)
                        Log.d("createTest", "post_key ${data!!.post_key}")
                        startActivity(intent)

                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d("createTest", "응답오류" + t.message.toString())
                }

            })
        }
    }

}