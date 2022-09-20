package com.wagle.kakao_app.GuestBook.SearchPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.PostUserHeart
import com.wagle.kakao_app.DTO.userHeartDTO
import com.wagle.kakao_app.DTO.user_placeDTO
import com.wagle.kakao_app.GusetBookModul.GuestAdapter.CommentAdapter
import com.wagle.kakao_app.GusetBookModul.SearchPage.create_commet
//import com.example.kakao_app.GusetBookModul.SearchPage.testDialog
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wagle.kakao_app.GusetBookModul.GuestAdapter.commentHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException


class ImageClick_Actitivty : AppCompatActivity() {

    private val call by lazy { RetrofitClass.getInstance() }
    var post_gp_key: Int? = null
    lateinit var comment_Adater : CommentAdapter
    lateinit var commentRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_click_actitivty)

        // 가져온 값 data에 저장 / 키 값이 없다면 default value 값을 설정해야 함
        val data = intent.getIntExtra("data", 0)
        val search_main_data = intent.getIntExtra("search_main_data", 0)
        val search_data = intent.getIntExtra("search_result", 0)
        val lank_data = intent.getIntExtra("lank_data", 0)
        val page = 0
        val create_comment : FloatingActionButton = findViewById(R.id.create_comment)
        commentRecyclerView = findViewById(R.id.comment_recyclerView)
        val titleImage : ImageView = findViewById(R.id.title_image)
        val userImage : ImageView = findViewById(R.id.user_image)
        val place : TextView = findViewById(R.id.place)
        val address : TextView = findViewById(R.id.place_address)
        val heart : TextView = findViewById(R.id.count_heart)
        val username : TextView = findViewById(R.id.nickname)
        val comment : TextView = findViewById(R.id.comment_place)
        val click_heart : ImageView = findViewById(R.id.heart_change)

        Log.d("key_data", "$data $search_data $lank_data")

        val user_key = MySharedPreferences.getUserKey(this)
        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        comment_Adater = CommentAdapter()
        commentRecyclerView.adapter = comment_Adater
        commentRecyclerView.layoutManager = LinearLayoutManager(this@ImageClick_Actitivty, LinearLayoutManager.VERTICAL, false )

        // 서치 쪽은 guestBook/search -> 눌렀을 때 ImageClick_Activity로 이동한다
        call?.GetLankResult(user_key,data or search_main_data or lank_data  or search_data)?.enqueue(object : Callback<user_placeDTO> {
            override fun onResponse(call: Call<user_placeDTO>, response: Response<user_placeDTO>) {
                if (response.isSuccessful) {
                    val response : user_placeDTO? = response.body()


                    Log.d("gp_key_data", "전송 : ${response!!.readResult[0].gp_key}")
                    Log.d("gp_key_data", "전송 : ${response!!.readResult[0].img}")

                    val titleUrl = "http://oceanit.synology.me:8001/public/images/guestPlace/" +
                            response!!.readResult[0].gp_key + "." + response.readResult[0].img

                    create_comment.setOnClickListener {
                        if (!user_key.isNullOrEmpty()) {
                            val intent = Intent(this@ImageClick_Actitivty, create_commet::class.java)
                            val send_data = response.readResult[0].gp_key
                            intent.putExtra("send_gp_key", send_data)
                            Log.d("gp_key_data", "전송 : $send_data")
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@ImageClick_Actitivty, "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    Glide.with(applicationContext)
                        .load(titleUrl)
                        .into(titleImage)

                    val userUrl = response.readResult[0].user_img

                    Glide.with(applicationContext)
                        .load(userUrl)
                        .circleCrop()
                        .into(userImage)

                    username.text = response.readResult[0].nickname
                    address.text = response.readResult[0].address
                    place.text = response.readResult[0].place
                    comment.text = response.readResult[0].comment
                    heart.text = response.readResult[0].heart.toString()
                    post_gp_key = response.readResult[0].gp_key

                    userHeartDTO(post_gp_key!!)

                    fun <T> isPresent(arr: List<T>, target: T): Boolean {
                        return target in arr
                    }

                    var result_boolean : Boolean

                    fun main() {

                        var user_data = response.readResult[0].heart_user

                        try {

                            val arr = user_data.split(",")
                            Log.d("logotype", " arr 값 :  $arr")

                            val key_data = user_key

                            result_boolean = isPresent(arr, key_data)
                            Log.d("logotype", " result_boolean 값 :  $result_boolean")

                            if (result_boolean){
                                click_heart.setImageResource(R.drawable.favorite_small_full)

                                click_heart.setOnClickListener {

                                    if (result_boolean) {
                                        click_heart.setImageResource(R.drawable.favorite_smell)
                                        heart.text = (response.readResult[0].heart - 1).toString()
                                        result_boolean = false
                                        Heart_Update(user_key, userHeartDTO(post_gp_key!!))
                                        Log.d("logotype", "if 처음 result_boolean == true")

                                    } else {
                                        Log.d("logotype", "else 처음 result_boolean ==false")
                                        heart.text = (response.readResult[0].heart).toString()
                                        click_heart.setImageResource(R.drawable.favorite_small_full)
                                        result_boolean = true
                                        Heart_Update(user_key, userHeartDTO(post_gp_key!!))
                                    }
                                }
                            } else {
                                click_heart.setImageResource(R.drawable.favorite_smell)

                                click_heart.setOnClickListener {

                                    if (result_boolean) {
                                        click_heart.setImageResource(R.drawable.favorite_smell)
                                        heart.text = (response.readResult[0].heart).toString()
                                        result_boolean = false
                                        Heart_Update(user_key, userHeartDTO(post_gp_key!!))
                                        Log.d("logotype", "if 처음 result_boolean == true")

                                    } else {
                                        Log.d("logotype", "else 처음 result_boolean ==false")
                                        heart.text = (response.readResult[0].heart + 1).toString()
                                        click_heart.setImageResource(R.drawable.favorite_small_full)
                                        result_boolean = true
                                        Heart_Update(user_key, userHeartDTO(post_gp_key!!))
                                    }
                                }
                            }
                        } catch (e: NullPointerException){

                            click_heart.setImageResource(R.drawable.favorite_smell)
                            result_boolean = false

                            click_heart.setOnClickListener {
                                if (result_boolean) {
                                    Log.d("logotype", "null true")
                                    click_heart.setImageResource(R.drawable.favorite_smell)
                                    heart.text = (response.readResult[0].heart).toString()
                                    result_boolean = false
                                    Heart_Update(user_key, userHeartDTO(post_gp_key!!))

                                } else {
                                    Log.d("logotype", "null false")
                                    heart.text = (response.readResult[0].heart + 1).toString()
                                    click_heart.setImageResource(R.drawable.favorite_small_full)
                                    result_boolean = true
                                    Heart_Update(user_key, userHeartDTO(post_gp_key!!))
                                }
                            }
                        }
                    }

                    main()


                } else {
                    Log.d("response", "Response 에러")
                }

            }

            override fun onFailure(call: Call<user_placeDTO>, t: Throwable) {
                Log.d("response", "Response 타임 에러" + t.message.toString())
            }
        })

        call?.GetReadComment(user_key,data or search_main_data or lank_data, page)?.enqueue(object : Callback<user_placeDTO>{
            override fun onResponse(call: Call<user_placeDTO>, response: Response<user_placeDTO>) {
                if (response.isSuccessful){
                    var response : user_placeDTO? = response.body()
                    Log.d("response", "Response1 성공 ${response?.readResult}")

                    val DataList = response?.readResult

                    comment_Adater.addPosts(DataList!!)
                }
            }
            override fun onFailure(call: Call<user_placeDTO>, t: Throwable) {

            }
        })
    }



    private fun Heart_Update(user_key: String, userHeartDTO: userHeartDTO ) {
        call?.PostHeartupdate(user_key, userHeartDTO)?.enqueue(object : Callback<PostUserHeart>{
            override fun onResponse(call: Call<PostUserHeart>, response: Response<PostUserHeart>,
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    Log.d("logotype", "$data")
                }
            }

            override fun onFailure(call: Call<PostUserHeart>, t: Throwable) {
                Log.d("logotype", "응답오류 " + t.message.toString())
            }
        })
    }
}

