package com.wagle.kakao_app.MyPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.wagle.kakao_app.DTO.DecorationDTO
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DecorationCustomDialog (context: Context) {
    private val dialog = Dialog(context)
    val result by lazy { RetrofitClass.getInstance() }

    fun showDia(user_key: String) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.decoration_dialog)

        val imageView1: ImageView = dialog.findViewById(R.id.imageView8)
        imageView1.setImageResource(R.drawable.deco_1)
        val imageView2: ImageView = dialog.findViewById(R.id.imageView9)
        imageView2.setImageResource(R.drawable.deco_2)
        val imageView3: ImageView = dialog.findViewById(R.id.imageView10)
        imageView3.setImageResource(R.drawable.deco_3)
        val imageView4: ImageView = dialog.findViewById(R.id.imageView11)
        imageView4.setImageResource(R.drawable.deco_4)
        val imageView5: ImageView = dialog.findViewById(R.id.imageView12)
        imageView5.setImageResource(R.drawable.deco_5)
        val imageView6: ImageView = dialog.findViewById(R.id.imageView13)
        imageView6.setImageResource(R.drawable.deco_6)
        val imageView7: ImageView = dialog.findViewById(R.id.imageView14)
        imageView7.setImageResource(R.drawable.deco_7)
        val imageView8: ImageView = dialog.findViewById(R.id.imageView15)
        imageView8.setImageResource(R.drawable.deco_8)
        val imageView9: ImageView = dialog.findViewById(R.id.imageView16)
        imageView9.setImageResource(R.drawable.deco_9)
        val imageView10: ImageView = dialog.findViewById(R.id.imageView17)
        imageView10.setImageResource(R.drawable.deco_10)
        val imageView11: ImageView = dialog.findViewById(R.id.imageView18)
        imageView11.setImageResource(R.drawable.deco_11)
        val imageView12: ImageView = dialog.findViewById(R.id.imageView19)
        imageView12.setImageResource(R.drawable.deco_12)
        val imageView13: ImageView = dialog.findViewById(R.id.imageView20)
        imageView13.setImageResource(R.drawable.deco_13)
        val imageView14: ImageView = dialog.findViewById(R.id.imageView21)
        imageView14.setImageResource(R.drawable.deco_14)
        val imageView15: ImageView = dialog.findViewById(R.id.imageView22)
        imageView15.setImageResource(R.drawable.deco_15)

        var imgArr : ArrayList<ImageView> = arrayListOf()

        imgArr.clear()
        imgArr.add(imageView1)
        imgArr.add(imageView1)
        imgArr.add(imageView2)
        imgArr.add(imageView3)
        imgArr.add(imageView4)
        imgArr.add(imageView5)
        imgArr.add(imageView6)
        imgArr.add(imageView7)
        imgArr.add(imageView8)
        imgArr.add(imageView9)
        imgArr.add(imageView10)
        imgArr.add(imageView11)
        imgArr.add(imageView12)
        imgArr.add(imageView13)
        imgArr.add(imageView14)
        imgArr.add(imageView15)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val x_button = dialog.findViewById<ImageView>(R.id.imageView7)

        x_button.setOnClickListener {
            dialog.dismiss()
        }

        val deco_index = ArrayList<String>()

        result?.GetMyDeco(user_key)?.enqueue(object : Callback<DecorationDTO> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<DecorationDTO>,
                response: Response<DecorationDTO>
            ) {
                if (response.isSuccessful) {
                    val response : DecorationDTO? = response.body()

                    Log.d("len", "${response!!.db_data.size}")

                    for(x in response!!.db_data.indices) {
                        deco_index.add(response!!.db_data[x]?.deco_key.toString())
                    }

                    Log.d("arr", "${deco_index}")

                    for(x in deco_index) {
                        when (x) {
                            "1" -> {
                                imageView1?.alpha = 1F
                            }
                            "2" -> {
                                imageView2?.alpha = 1F
                            }
                            "3" -> {
                                imageView3?.alpha = 1F
                            }
                            "4" -> {
                                imageView4?.alpha = 1F
                            }
                            "5" -> {
                                imageView5?.alpha = 1F
                            }
                            "6" -> {
                                imageView6?.alpha = 1F
                            }
                            "7" -> {
                                imageView7?.alpha = 1F
                            }
                            "8" -> {
                                imageView8?.alpha = 1F
                            }
                            "9" -> {
                                imageView9?.alpha = 1F
                            }
                            "10" -> {
                                imageView10?.alpha = 1F
                            }
                            "11" -> {
                                imageView11?.alpha = 1F
                            }
                            "12" -> {
                                imageView12?.alpha = 1F
                            }
                            "13" -> {
                                imageView13?.alpha = 1F
                            }
                            "14" -> {
                                imageView14?.alpha = 1F
                            }
                            "15" -> {
                                imageView15?.alpha = 1F
                            }
                        }
                    }

                    for(x in deco_index) {
                        // var data : Int

                        var index: Int = x.toInt() // index
//                            val intent = Intent(dialog.context, MyPageFragment::class.java)
//                            intent.putExtra("data", x.toInt())
//                            ActivityCompat.startActivity(dialog.context, intent, null)

                        imgArr[x.toInt()].setOnClickListener {
                            onClickedListener.onClicked(index)
                            dialog.dismiss()
                        }

//                            // activit로 넘기는거
//                            sendbutton.setOnClickListener {
//                                onClickListener.onClicked(data)
//                            }
                    }
                }
                else {
                    Log.d("test3", "오류")
                }

            }
            override fun onFailure(call: Call<DecorationDTO>, t: Throwable) {
            }
        })


        dialog.show()
    }

//    fun send(){
//
//        val sendbutton = dialog.findViewById<AppCompatButton>(R.id.sendButton)
//
//        sendbutton.setOnClickListener {
//            onClickListener.onClicked()
//        }
//    }

    interface ButtonClickListener{
        fun onClicked(deco: Int)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener){
        onClickedListener = listener
    }
}