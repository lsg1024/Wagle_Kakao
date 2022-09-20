package com.wagle.kakao_app.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.wagle.kakao_app.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    lateinit var viewPager2: ViewPager2
    var wait:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewpager2)
        val tabLayout : TabLayout = findViewById(R.id.tabLayout)

        viewPager2.apply {
            adapter = FragmentAdapter(context as FragmentActivity)
            // 사용자 스와이프에 화면 전환은 불가능하게 만듬 -> 맵을 이동할 때 발생하는 화면 전환 문제
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager2) {tab, position ->
            when (position){
                0 -> {
                    tab.setIcon(R.drawable.ic_tab_home_off)
                    tab.text = "홈"
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_tab_feed_off)
                    tab.text = "동행"
                }
                2 -> {
                    tab.setIcon(R.drawable.ic_tab_partner_off)
                    tab.text = "방명록"
                }
                3 -> {
                    tab.setIcon(R.drawable.ic_tab_traveldiary_off)
                    tab.text = "여행일지"
                }
                4 -> {
                    tab.setIcon(R.drawable.ic_tab_mypage)
                    tab.text = "마이 페이지"
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - wait >= 2000) {
            wait = System.currentTimeMillis()
            Snackbar.make(viewPager2, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Snackbar.LENGTH_LONG).show()
        } else {
            finish()
        }


    }
}
