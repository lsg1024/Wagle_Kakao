package com.wagle.kakao_app.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.GuestRankDTO
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainBannerFragment(private var index : Int) : Fragment() {

    val call by lazy { RetrofitClass.getInstance() }

    lateinit var main_viewPager_image : ImageView
    lateinit var main_guestBook_heart : TextView
    lateinit var main_guestBook_name : TextView
    lateinit var main_guestBook_address : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main_banner, container, false)

        main_viewPager_image = view.findViewById(R.id.main_viewPager_image)
        main_guestBook_heart = view.findViewById(R.id.main_guestBook_heart)
        main_guestBook_name = view.findViewById(R.id.main_guestBook_name)
        main_guestBook_address = view.findViewById(R.id.main_guestBook_address)


        val url = "http://oceanit.synology.me:8001/public/images/guestPlace/"


        call?.GetlLankplace(user_key = "")?.enqueue(object : Callback<GuestRankDTO> {
            override fun onResponse(call: Call<GuestRankDTO>, response: Response<GuestRankDTO>
            ) {
                if(response.isSuccessful){
                    val response : GuestRankDTO? = response.body()
                    Log.d("guest", "$response")

                    if(response?.result?.size != 0){
                        Glide.with(view.context)
                            .load(url + response?.result?.get(index)?.gp_key + "." + response?.result?.get(index)?.img )
                            .into(main_viewPager_image)

                        main_guestBook_heart.text =  response!!.result[index].heart.toString()
                        main_guestBook_name.text = response!!.result[index].place
                        main_guestBook_address.text = response!!.result[index].address
                    }

                }

            }

            override fun onFailure(call: Call<GuestRankDTO>, t: Throwable) {

            }

        })


        return view
    }

}