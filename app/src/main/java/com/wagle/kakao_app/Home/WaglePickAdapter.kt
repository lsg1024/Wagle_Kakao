package com.wagle.kakao_app.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WaglePickAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int  = 5

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> WaglePickFragment(0)
            1 -> WaglePickFragment(1)
            2 -> WaglePickFragment(2)
            3 -> WaglePickFragment(3)
            else -> WaglePickFragment(4)

        }
    }
}