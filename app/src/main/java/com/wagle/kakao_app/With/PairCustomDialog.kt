package com.wagle.kakao_app.With

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import com.wagle.kakao_app.R

class PairCustomDialog(context: Context) {
    private val dialog = Dialog(context)

    fun showDia(context: Context) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)

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
            dialog.dismiss()

            val rating_dialog = RatingDialog(context)
            rating_dialog.showRatingDia()
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