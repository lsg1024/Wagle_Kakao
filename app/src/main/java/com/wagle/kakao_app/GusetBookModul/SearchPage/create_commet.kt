package com.wagle.kakao_app.GusetBookModul.SearchPage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.comment_response
import com.wagle.kakao_app.GuestBook.SearchPackage.ImageClick_Actitivty
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class create_commet : AppCompatActivity() {

    private val call by lazy { RetrofitClass.getInstance() }
    private var imageView: ImageView? = null
    private var comment : EditText? = null
    private var request_place : Button? = null
    private val OPEN_GALLERY = 1
    private var body_Data : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_commet)

        imageView = findViewById(R.id.setImage)
        comment = findViewById(R.id.dialog_comment)
        request_place = findViewById(R.id.createcomment)
    }


    override fun onStart() {
        super.onStart()

        val gp_key = intent.getIntExtra("send_gp_key", 0)
        val user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        imageView?.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)
        }


        request_place?.setOnClickListener {
            onClick(gp_key)
            finish()
        }

        Log.d("gp_key_data", "${user_key}" + "$gp_key" + "\n${comment?.text.toString()}" +  "$imageView" + "$body_Data")

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return
        } else {
            permissionCheck == PackageManager.PERMISSION_GRANTED
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 99)

            Toast.makeText(this, "파일 및 미디어 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

    }
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "잘못된 접근입니다", Toast.LENGTH_SHORT).show()
            return
        }

        when (requestCode) {
            OPEN_GALLERY -> {
                val selectedImageURI: Uri? = data?.data

                if (selectedImageURI != null) {

                    var result = ""

                    if (selectedImageURI.scheme.equals("content")) {
                        val cursor =
                            this.contentResolver.query(selectedImageURI, null, null, null, null)
                        cursor.use { cursor ->
                            if (cursor != null) {
                                cursor.moveToFirst()

                                result =
                                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                            }
                        }
                    }

                    if (result == null) {
                        result = selectedImageURI.path.toString()
                        var cut = result.lastIndexOf("/")
                        if (cut != -1) {
                            result = result.substring(cut + 1)
                        }
                    }
                    Log.d("send_image", " ${contentResolver.getType(selectedImageURI)}")

                    val imgae_Type = contentResolver.getType(selectedImageURI)

                    val file = File(result)
                    val requestBody = RequestBody.create(MediaType.parse("$imgae_Type"), file)
                    body_Data = MultipartBody.Part.createFormData("img", file.name, requestBody)

                    Glide.with(this).load(selectedImageURI).into(imageView!!)

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "잘못된 접근입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun onClick(gp_key : Int){

        val user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        Log.d("gp_key_data", "${user_key}" + "$gp_key" + "\n${comment?.text.toString()}" +  "$imageView" + "$body_Data")

        if (gp_key != null && comment?.text != null && imageView != null && body_Data != null){
            createRetrofit(user_key , gp_key, comment!!.text.toString(), body_Data!!)
            Toast.makeText(this, "서버에 데이터를 전송하였습니다", Toast.LENGTH_LONG).show()

        } else {
            Log.d("gp_key_data", "$gp_key" + "\n${comment?.text.toString()}" +  "$imageView" + "$body_Data")
            Toast.makeText(this, "입력이 되지 않은 값이 있습니다 ", Toast.LENGTH_LONG).show()
        }
    }

    fun createRetrofit(user_key : String, gp_key: Int, comment : String, body : MultipartBody.Part){
        call?.PostCreateBook(user_key, gp_key, comment, body)?.enqueue(object : Callback<comment_response> {
            override fun onResponse(call: Call<comment_response>, response: Response<comment_response>,
            ) {
                if (response.isSuccessful){
                    val result1 = response.body().toString()

                    Log.d("send_image", "$result1")
                } else {
                    Log.d("send_image", "실패")
                }
            }

            override fun onFailure(call: Call<comment_response>, t: Throwable) {
                Log.d("send_image", "응답 실패  " + t.message.toString())
            }
        })
    }
}
