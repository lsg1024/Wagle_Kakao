package com.wagle.kakao_app.GusetBookModul

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.ResponsePlace
import com.wagle.kakao_app.Home.MainActivity
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
import java.util.*

class Create_place : AppCompatActivity() {

    private val OPEN_GALLERY = 1
    private var setImage: ImageView? = null
    private var setText: TextView? = null
    private var request_place: AppCompatButton? = null
    private var setplace: EditText? = null
    private var comment: EditText? = null
    private var body_Data: MultipartBody.Part? = null

    private val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)

        comment = findViewById(R.id.comment)
        request_place = findViewById(R.id.create_place)
        setImage = findViewById(R.id.setImage)
        setText = findViewById(R.id.setaddress)
        setplace = findViewById(R.id.setPlace)

        setImage?.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)

        }

        request_place?.setOnClickListener {
            onClick()
            finish()
        }
        val chick_buttom : TextView = findViewById(R.id.click_Buttom3)


        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_MEDIA_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            Log.d("permission", "Q 권한")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION), 99)
            Toast.makeText(this, "파일 및 미디어 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()

            chick_buttom.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://oceanit.synology.me:8001/admin/privacy")
                startActivity(intent)
            }
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 99)
            Log.d("permission", "M 권한")
            Toast.makeText(this, "파일 및 미디어 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()

            chick_buttom.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://oceanit.synology.me:8001/admin/privacy")
                startActivity(intent)
            }

            return
        }
    }


    fun onClick() {

        val user_key = MySharedPreferences.getUserKey(this)

        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        if (comment?.text != null && setplace?.text != null && setText?.text != null && setImage != null && body_Data != null) {
            createRetrofit(user_key,
                setplace!!.text.toString(),
                setText!!.text.toString(),
                comment!!.text.toString(),
                body_Data!!)
            Toast.makeText(this, "서버에 장소 등록을 요청하였습니다", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "입력이 되지 않은 값이 있습니다 ", Toast.LENGTH_LONG).show()
        }
    }

    fun createRetrofit(
        user_key: String,
        setPlace: String,
        address: String,
        des: String,
        body: MultipartBody.Part
    ) {
        call?.PostCreatePlace(user_key, setPlace, address, des, body)
            ?.enqueue(object : Callback<ResponsePlace> {
                override fun onResponse(
                    call: Call<ResponsePlace>, response: Response<ResponsePlace>,
                ) {
                    if (response.isSuccessful) {
                        val result1 = response.body().toString()

                        Log.d("send_image", "$result1")
                    } else {
                        Log.d("send_image", "실패")
                    }
                }

                override fun onFailure(call: Call<ResponsePlace>, t: Throwable) {
                    Log.d("send_image", "응답 실패  " + t.message.toString())
                }
            })
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

                    Log.d("send_image", " $result")
                    Log.d("send_image", "$requestBody")
                    Log.d("send_image", "$body_Data")
                    Log.d("setImage", result)

//                    val exif = ExifInterface(result)
//                    exif.latLong?.get(0) // 위도 latitude
//                    exif.latLong?.get(1) // 경도 longitude


                    var address = "주소 변환 실패 \nGPS좌표가 있는 다른 사진을 넣어주세요"

                    try {
                        val exif = ExifInterface(result)
                        exif.latLong?.get(0) // 위도 latitude
                        exif.latLong?.get(1) // 경도 longitude
                        val latitude= exif.latLong?.get(0)
                        val longitude = exif.latLong?.get(1)
                        Log.d("send_image", "$exif + ${exif.latLong?.get(0)} + ${exif.latLong?.get(1)}")
                        val geocoder = Geocoder(this, Locale.KOREA)
                        address = geocoder.getFromLocation(latitude!!, longitude!!, 1).first().getAddressLine(0)
                        Log.d("address", address)
                        setText?.text = address


                    } catch (e: Exception){
                        e.printStackTrace()
                        setText?.text = address
                    }

                    Glide.with(this).load(selectedImageURI).into(setImage!!)

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "잘못된 접근입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
