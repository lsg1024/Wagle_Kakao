package com.wagle.kakao_app.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class MAinAd1FragmentAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int  = 1

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            /*0 -> MainAd1Fragment()*/
            else -> MainAd1Fragment()
        }
    }
}