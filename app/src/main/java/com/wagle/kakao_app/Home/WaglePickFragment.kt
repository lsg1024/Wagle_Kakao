package com.wagle.kakao_app.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wagle.kakao_app.DTO.WaglePickDTO
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Response

class WaglePickFragment (private val index : Int) : Fragment() {

    val call by lazy { RetrofitClass.getInstance() }

    lateinit var waglepick_profile : ImageView
    lateinit var walgePick_friendsName : TextView
    lateinit var waglePick_distance : TextView
    lateinit var wagle_intro : TextView


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
        val view =  inflater.inflate(R.layout.fragment_wagle_pick, container, false)

        waglepick_profile = view.findViewById(R.id.waglePick_pofile)
        walgePick_friendsName = view.findViewById(R.id.walgePick_friendsName)
        waglePick_distance = view.findViewById(R.id.distance)
        wagle_intro = view.findViewById(R.id.intro)


        call?.get_waglePick()?.enqueue(object : retrofit2.Callback<WaglePickDTO> {
            override fun onResponse(call: Call<WaglePickDTO>, response: Response<WaglePickDTO>) {
                Log.d("waglePick", "$response")

                val result : WaglePickDTO? = response.body()

                if(result?.result?.size != 0){
                    Glide.with(view.context)
                        .load(result?.result?.get(index)?.img)
                        .into(waglepick_profile)

                    walgePick_friendsName.text = result!!.result[index].nickname
                    waglePick_distance.text = result!!.result[index].temperature.toString()
                    wagle_intro.text = result!!.result[index].intro
                }
            }

            override fun onFailure(call: Call<WaglePickDTO>, t: Throwable) {

            }

        })






        return view
    }
}