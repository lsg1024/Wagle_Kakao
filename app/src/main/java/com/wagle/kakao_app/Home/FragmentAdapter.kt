package com.wagle.kakao_app.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wagle.kakao_app.GusetBookModul.GuestBookFragment
import com.wagle.kakao_app.MyPage.MyPageFragment
import com.wagle.kakao_app.TrapLog.TrapFragment
import com.wagle.kakao_app.With.WithFragment

class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val NUM_PAGES = 5

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            1 -> WithFragment()
            2 -> GuestBookFragment()
            3 -> TrapFragment()
            else -> MyPageFragment()
        }
    }
}