package com.wagle.kakao_app.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa)  {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> MainBannerFragment(0)
            1 -> MainBannerFragment(1)
            2 -> MainBannerFragment(2)
            3 -> MainBannerFragment(3)
            else -> MainBannerFragment(4)

        }
    }
}




//class BannerFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa)  {
//
//    val call by lazy { RetrofitClass.getInstance() }
//
//    override fun getItemCount(): Int = 5
//
//    override fun createFragment(position: Int): Fragment {
//
//        val gp_key = arrayListOf<Int>()
//        val img = arrayListOf<String>()
//        val heart = arrayListOf<Int>()
//        val place = arrayListOf<String>()
//        val address = arrayListOf<String>()
//
//        val url = "http://oceanit.synology.me:8001/public/images/guestPlace/"
//
//        call?.GetlLankplace("")?.enqueue(object : Callback<GuestRankDTO>{
//
//            override fun onResponse(call: Call<GuestRankDTO>, response: Response<GuestRankDTO>) {
//                if (response.isSuccessful) {
//                    val result : GuestRankDTO = response.body()!!
//
//                    for (i in result.result.indices){
//                        val data1 = result.result[i].gp_key
//                        val data2 = result.result[i].img
//                        val data3 = result.result[i].heart
//                        val data4 = result.result[i].place
//                        val data5 = result.result[i].address
//
//                        var imgUrl = url + data1 + data2
//
//
//                        gp_key.add(data1)
//                        img.add(imgUrl)
//                        heart.add(data3)
//                        place.add(data4)
//                        address.add(data5)
//                    }
//                }
//            }
//            override fun onFailure(call: Call<GuestRankDTO>, t: Throwable) {
//            }
//        })
//
//
//        return when (position) {
//            0 -> MainBannerFragment(
////                img[1],
//                heart[0], place[0], address[0])
//            1 -> MainBannerFragment(
////                img[1],
//                heart[1], place[1], address[1])
//            2 -> MainBannerFragment(
////                img[2],
//                heart[2], place[2], address[2])
//            3 -> MainBannerFragment(
////                img[3],
//                heart[3], place[3], address[3])
//            else -> MainBannerFragment(
////                img[4],
//                heart[4], place[4], address[4])
//        }
//    }
//
//
//    fun test(){
//        call?.GetlLankplace("")?.enqueue(object : Callback<GuestRankDTO>{
//            override fun onResponse(call: Call<GuestRankDTO>, response: Response<GuestRankDTO>) {
//                if (response.isSuccessful) {
//                    val result : GuestRankDTO = response.body()!!
//
//                    result.result[0]
//
//
//                }
//            }
//
//            override fun onFailure(call: Call<GuestRankDTO>, t: Throwable) {
//
//            }
//
//        })
//
//    }
//}
