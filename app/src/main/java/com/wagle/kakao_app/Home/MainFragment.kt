package com.wagle.kakao_app.Home

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
import androidx.viewpager2.widget.ViewPager2
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingList
import com.wagle.kakao_app.Pair.Pair_MainActivity
import com.wagle.kakao_app.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.With.WithFragment


class MainFragment : Fragment() {

//    private val main_ad_viewPager = view?.findViewById<ViewPager2>(R.id.main_ad_viewPager)

    lateinit var wifiButton : ImageButton
    lateinit var notification : ImageButton
    lateinit var chattingButton : ImageButton
    lateinit var user_key : String
    lateinit var mainActivity: MainActivity

//    private val sliderImageHandler : Handler = Handler()
//    private val main_ad_viewPagerRunnable = Runnable { main_ad_viewPager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen

        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        mainActivity = context as MainActivity

        wifiButton = view.findViewById(R.id.wifiButton)
        notification = view.findViewById(R.id.notification_1)
        chattingButton = view.findViewById(R.id.chat)

        user_key = MySharedPreferences.getUserKey(mainActivity)

        if (MySharedPreferences.getUserKey(mainActivity).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(mainActivity)}")
        }

        wifiButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, Pair_MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }

        }

        notification.setOnClickListener {
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


//        // 메인 광고
//        val main_ad1_image = view.findViewById<Button>(R.id.intent_with)
//
//        main_ad1_image.setOnClickListener{
//
//        }

        // 메인 방명록
        val main_guestBook_viewPager = view.findViewById<ViewPager2>(R.id.main_guestBook_viewPager)
        val main_cicleIndicator1 = view.findViewById<TabLayout>(R.id.main_cicleIndicator)

        main_guestBook_viewPager.apply {
            adapter = BannerFragmentAdapter(context as FragmentActivity)
        }

        TabLayoutMediator(main_cicleIndicator1, main_guestBook_viewPager) {tab, position ->
            when(position){
                0 -> {}
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
            }
        }.attach()


        // 메인 와글픽 친구
        val main_waglePick_viewPager = view.findViewById<ViewPager2>(R.id.main_waglePick_viewPager)
        val main_circleIndicator2 = view.findViewById<TabLayout>(R.id.main_cicleIndicator2)

        main_waglePick_viewPager.apply {
            adapter =  WaglePickAdapter(context as FragmentActivity)
        }

        TabLayoutMediator(main_circleIndicator2, main_waglePick_viewPager) {tab, position ->
            when(position){
                0 -> {}
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
            }
        }.attach()

        return view
    }
}