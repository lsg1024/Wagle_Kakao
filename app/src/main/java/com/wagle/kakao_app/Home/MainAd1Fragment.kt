package com.wagle.kakao_app.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.wagle.kakao_app.R


class MainAd1Fragment : Fragment() {


    lateinit var mainActivity: MainActivity
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main_ad1, container, false)

        button = view.findViewById(R.id.intent_with)
        mainActivity = context as MainActivity

        button.setOnClickListener {

        }

        return view
    }
}
