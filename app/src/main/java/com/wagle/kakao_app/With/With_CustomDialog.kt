package com.wagle.kakao_app.With

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.wagle.kakao_app.R

class With_CustomDialog(context: Context) {

    private val dialog = Dialog(context)

    fun showDia(){
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val content = dialog.findViewById<TextView>(R.id.textView17)
        val x_button = dialog.findViewById<Button>(R.id.button5)
        val y_button = dialog.findViewById<Button>(R.id.button)
        val n_button = dialog.findViewById<Button>(R.id.button4)

        content.setText("참여하시겠습니까?")

        x_button.setOnClickListener {
            dialog.dismiss()
        }

        y_button.setOnClickListener {
            dialog.dismiss()
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