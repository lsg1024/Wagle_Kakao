package com.wagle.kakao_app.With

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wagle.finaltest.DTO.WithFragmentDTO
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingList
import com.wagle.kakao_app.Home.WaglePickAdapter
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.Pair.Pair_MainActivity
import com.wagle.kakao_app.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wagle.kakao_app.Home.MainActivity
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.With.adapter.WithMain_ClosingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WithFragment : Fragment() {

//    val indicatorAdapter by lazy { IndicatorAdapter(support) }

    // adapter
    lateinit var with_real_adapter : WithMain_RealtimeAdapter
    lateinit var with_closing_adapter : WithMain_ClosingAdapter

    lateinit var user_key : String
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mainActivity = context as MainActivity


        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(mainActivity)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }

        val view =  inflater.inflate(R.layout.fragment_with, container, false)

        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.cicleIndicator)

        val search_btn = view.findViewById<Button>(R.id.search_imageButton)
        val viewmore_btn1 = view.findViewById<Button>(R.id.viewmore_btn1)
        val viewmore_btn2 = view.findViewById<Button>(R.id.viewmore_btn2)
        val newWrite_imageButton : ImageButton= view.findViewById(R.id.newWrite_imageButton)
        val wifiButton : ImageButton = view.findViewById(R.id.wifiButton)
        val notificationButton : ImageButton = view.findViewById(R.id.notificationButton)
        val chattingButton : ImageButton = view.findViewById(R.id.chattingButton)

        val withMain_realtimeList = view.findViewById<RecyclerView>(R.id.withMain_realtimeList)
        val withMain_comingsoonList = view.findViewById<RecyclerView>(R.id.withMain_comingsoonList)

        // retrofit2 사용하기 위해서
        val result by lazy { RetrofitClass.getInstance() }

        search_btn.setOnClickListener {
            val intent = Intent(context, With_searchPageActivity::class.java)
            startActivity(intent)
        }

        viewmore_btn1.setOnClickListener {
            val intent = Intent(context, RealtimeList::class.java)
            startActivity(intent)
        }

        viewmore_btn2.setOnClickListener {
            val intent = Intent(context, ComingsoonListActivity::class.java)
            startActivity(intent)
        }

        newWrite_imageButton.setOnClickListener {

            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, newWrite_WithActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        wifiButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, Pair_MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }

        }

        notificationButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, AlarmList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        chattingButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, ChattingList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 와글픽 친구추천 viewPager2 indicator

        viewPager2.apply {
            adapter = WaglePickAdapter(context as FragmentActivity)

        }

        TabLayoutMediator(tabLayout, viewPager2) {tab, position ->
            when (position){
                0 -> {}
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
            }
        }.attach()



        result?.get_withMain()?.enqueue(object : Callback<WithFragmentDTO> {
            override fun onResponse(call: Call<WithFragmentDTO>, response: Response<WithFragmentDTO>) {
                if(response.isSuccessful){

                    // 메인 -> 최신 글
                    val response1 : WithFragmentDTO? = response.body()
                    Log.d("response", "성공 ${response1?.main_real_time_data!!}")

                    val withMain_RealtimeDataList =  response1?.main_real_time_data

                    val withMain_realtimeList = view.findViewById<RecyclerView>(R.id.withMain_realtimeList)
                    withMain_realtimeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    with_real_adapter = WithMain_RealtimeAdapter(user_key)
                    withMain_realtimeList.adapter = with_real_adapter

                    with_real_adapter.addPosts(withMain_RealtimeDataList)


                    // 메인 -> 곧 마감 글
                    val response2 : WithFragmentDTO? = response.body()
                    Log.d("respose2", "성공 ${response2?.main_closing_data!!}")

                    val withMain_ClosingDataList = response2?.main_closing_data

                    val withMain_comingsoonList = view.findViewById<RecyclerView>(R.id.withMain_comingsoonList)

                    withMain_comingsoonList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    with_closing_adapter = WithMain_ClosingAdapter(user_key)
                    withMain_comingsoonList.adapter = with_closing_adapter

                    with_closing_adapter.addPosts(withMain_ClosingDataList)

                } else {
                    Log.d("with_main error", "실패")
                }
            }

            override fun onFailure(call: Call<WithFragmentDTO>, t: Throwable) {
                Log.d("with main error", "응답 에러" + t.message.toString())
            }
        })
        return view
    }
}


//lateinit var with_real_adapter : WithMain_RealtimeAdapter
//lateinit var with_closing_adapter : WithMain_ClosingAdapter

//result?.get_withMain()?.enqueue(object : Callback<WithFragmentDTO> {
//    override fun onResponse(call: Call<WithFragmentDTO>, response: Response<WithFragmentDTO>) {
//        if(response.isSuccessful){
//
//            // 메인 -> 최신 글
//            val response1 : WithFragmentDTO? = response.body()
//            Log.d("response", "성공 ${response1?.main_real_time_data!!}")
//
//            val withMain_RealtimeDataList =  response1?.main_real_time_data
//
//            val withMain_realtimeList = view.findViewById<RecyclerView>(R.id.withMain_realtimeList)
//            withMain_realtimeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//            with_real_adapter = WithMain_RealtimeAdapter()
//            withMain_realtimeList.adapter = with_real_adapter
//
//            with_real_adapter.addPosts(withMain_RealtimeDataList)
//
//
//            // 메인 -> 곧 마감 글
//            val response2 : WithFragmentDTO? = response.body()
//            Log.d("respose2", "성공 ${response2?.main_closing_data!!}")
//
//            val withMain_ClosingDataList = response2?.main_closing_data
//
//            val withMain_comingsoonList = view.findViewById<RecyclerView>(R.id.withMain_comingsoonList)
//
//            withMain_comingsoonList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//            with_closing_adapter = WithMain_ClosingAdapter()
//            withMain_comingsoonList.adapter = with_closing_adapter
//
//            with_closing_adapter.addPosts(withMain_ClosingDataList)
//
//
//
//        } else {
//            Log.d("with_main error", "실패")
//        }
//    }
//
//    override fun onFailure(call: Call<WithFragmentDTO>, t: Throwable) {
//        Log.d("with main error", "응답 에러" + t.message.toString())
//    }
//})
