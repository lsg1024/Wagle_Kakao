package com.wagle.kakao_app.With

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wagle.kakao_app.R

class RatingDialog (context: Context) : AppCompatActivity(){

//    private lateinit var pairAdapter: PairAdapter
//    private val data = mutableListOf<PairData>()
//
//    lateinit var recyclerview : RecyclerView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.recyclerview_item)
//
//        recyclerview = findViewById(R.id.recyclerview)
//        initRecycler()
//    }
//
//
//    private fun initRecycler() {
//        pairAdapter = PairAdapter(this)
//
//        recyclerview.adapter = pairAdapter
//
//        data.apply {
//            add(PairData(user_img = R.drawable.favorite, user_name = "mary", rating = 4))
//            add(PairData(user_img = R.drawable.favorite, user_name = "mary", rating = 4))
//            add(PairData(user_img = R.drawable.favorite, user_name = "mary", rating = 4))
//            add(PairData(user_img = R.drawable.favorite, user_name = "mary", rating = 4))
//        }
///*김연정 바보 */
//        // pairAdapter.datas.apply { addAll(data) }
//        // pairAdapter.notifyDataSetChanged()
//    }

    private val dialog = Dialog(context)

    fun showRatingDia() {

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.pair_rating_dialog)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val x_button = dialog.findViewById<Button>(R.id.button5)
        val y_button = dialog.findViewById<Button>(R.id.button)
        val n_button = dialog.findViewById<Button>(R.id.button4)

        x_button.setOnClickListener {

            dialog.dismiss()
        }

        y_button.setOnClickListener {
            //DB 호출
            dialog.dismiss()
            finish()
        }

        n_button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    interface ButtonClickListener{
        fun onClicked(text: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }
}