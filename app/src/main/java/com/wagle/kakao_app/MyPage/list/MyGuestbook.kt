package com.wagle.kakao_app.MyPage.list

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.MyProfileDTO
import com.wagle.kakao_app.DTO.SearchResultDTO
import com.wagle.kakao_app.GuestBook.SearchPackage.PedAdapter
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyGuestbook : AppCompatActivity() {
    lateinit var user_key : String

    private val call by lazy { RetrofitClass.getInstance() }

    lateinit var back_btn : TextView

    lateinit var profile_img : CircleImageView
    lateinit var profile_nickname :TextView
    lateinit var profile_intro : TextView

    lateinit var recyclerView: RecyclerView
    lateinit var ped_Adapter : PedAdapter

    var page = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_guestbook)

        user_key = MySharedPreferences.getUserKey(this)

        back_btn = findViewById((R.id.back_btn))

        profile_img = findViewById(R.id.profile_img)
        profile_nickname = findViewById(R.id.profile_nickname)
        profile_intro = findViewById(R.id.profile_intro)


        recyclerView = findViewById(R.id.Myguest_recyclerView)

        ped_Adapter = PedAdapter()
        recyclerView.adapter = ped_Adapter

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        back_btn.setOnClickListener{
            finish()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItems = IntArray(2)
                (recyclerView.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(lastItems)
                val lastItem = Math.max(lastItems[0], lastItems[1])
                val itemTotalCount = recyclerView.adapter?.itemCount
                if(lastItem + 1 == itemTotalCount) {

                    mybook_retrofit()
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()

        getMyprofile()

        page = 0;
        ped_Adapter.clearData()
        mybook_retrofit()

    }


    fun getMyprofile(){
        call?.GetMyProfile(user_key)?.enqueue(object : retrofit2.Callback<MyProfileDTO>{
            override fun onResponse(call: Call<MyProfileDTO>, response: Response<MyProfileDTO>) {
                // val response: MyProfileDTO? = response.body()

                Log.d("MyProfile", "${response.body()?.MyProfile}")

                Glide.with(this@MyGuestbook)
                    .load(response.body()?.MyProfile!![0].img)
                    .into(profile_img)

                profile_nickname.text = response.body()?.MyProfile!![0].nickname
                profile_intro.text = response.body()?.MyProfile!![0].intro
            }

            override fun onFailure(call: Call<MyProfileDTO>, t: Throwable) {
                Log.e("MyProfile", t.message.toString())
            }

        })
    }





    fun mybook_retrofit() {
        call?.GetMyGuestbook(user_key, page)?.enqueue(object : Callback<SearchResultDTO>{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<SearchResultDTO>, response: Response<SearchResultDTO>,
            ) {
                if (response.isSuccessful) {

                    val response : SearchResultDTO? = response.body()

                    val dataList = response?.searchResult

                    if (dataList != null) {
                        ped_Adapter.addPosts(dataList)
                        page++

                    }

                } else {

                }
            }
            override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
            }
        })
    }

}