package com.wagle.kakao_app.Pair

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.wagle.kakao_app.DTO.QRPostDTO
import com.wagle.kakao_app.GusetBookModul.GuestAdapter.call
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class QR_Activity : AppCompatActivity() {

    lateinit var user_key : String
//    var get_guest_data by Delegates.notNull<Int>()
    val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val get_host_data = intent.getIntExtra("host_key", 1)


        user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        if (user_key.toInt() == get_host_data){

            Toast.makeText(this, "QR을 스캔해주세요. ", Toast.LENGTH_LONG).show()
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false) //세로
            integrator.setPrompt("QR을 동행하실 방장에게 보여주세요\n\n\n") // QR코드 화면이 되면 밑에 표시되는 글씨 바꿀수있음
            integrator.setBeepEnabled(false)
            integrator.initiateScan()

        } else {
            val imageViewQrCode: ImageView = findViewById(R.id.qrCode)
            // 큐알은 중간에 스트링으로 바꾸면 안되고 처음부터 바꿔야됨
            val text = intent.getStringExtra("guest_key").toString()
            createQRcode(imageViewQrCode, text)
        }
    }

    fun createQRcode(img: ImageView, text : String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            img.setImageBitmap(bitmap)

        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {

            val get_post_key = intent.getIntExtra("post_key", 1)
            Log.d("Result_key", "$get_post_key")

            if (result.contents == null) {
                Toast.makeText(this, "스캔에 실패했습니다", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "스캔에 성공했습니다", Toast.LENGTH_LONG).show()
                Log.d("Result_key", result.contents)

                val guest_user_key = result.contents.toInt()
                Log.d("Result_key", "$guest_user_key")

                call?.post_QRCheck(get_post_key, guest_user_key)?.enqueue(object : Callback<QRPostDTO>{
                    override fun onResponse(call: Call<QRPostDTO>, response: Response<QRPostDTO>) {
                        val last : QRPostDTO? = response.body()

                        if (last?.result == "success"){
                            val intent = Intent(this@QR_Activity, Pair_MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@QR_Activity, "알수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<QRPostDTO>, t: Throwable) {
                        Log.d("host_key", "dhf${t.message.toString()}")
                    }

                })

//                call?.post_QRCheck(get_post_key, guest_user_key)?.enqueue(object : Callback<QRPostDTO>{
//                    override fun onResponse(call: Call<QRPostDTO>, response: Response<QRPostDTO>) {
//
//                        val result : QRPostDTO? = response.body()
//
//                        Log.d("retrofit2_key", "${result}")
//                        Log.d("retrofit2_key", "${result?.result}")
//
//                        if (result?.result == "success"){
//                            val intent = Intent(this@QR_Activity, Pair_MainActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(this@QR_Activity, "알수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
//                            finish()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<QRPostDTO>, t: Throwable) {
////                        Toast.makeText(this@QR_Activity, "알수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
////                        finish()
//                        Log.d("host_key", "dhf${t.message.toString()}")
//                    }
//
//                })
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}