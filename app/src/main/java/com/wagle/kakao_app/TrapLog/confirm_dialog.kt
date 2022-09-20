package com.wagle.kakao_app.TrapLog

import android.content.Context
import android.view.View
import com.wagle.kakao_app.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class confirm_dialog(context: Context) : BottomSheetDialog(context){

    init {

        val view: View = layoutInflater.inflate(R.layout.comfirm_bottom_dialog, null)
        setContentView(view)

    }
}