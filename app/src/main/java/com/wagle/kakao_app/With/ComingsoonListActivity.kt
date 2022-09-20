package com.wagle.kakao_app.With

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.WithClosingDTO
import com.wagle.kakao_app.DTO.WithRealtimeDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.adapter.ComingsoonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComingsoonListActivity : AppCompatActivity() {


    // 페이징
    var page : Int = 0

    // user_key
    lateinit var user_key : String


    // retrofit
    val call by lazy { RetrofitClass.getInstance() }


    lateinit var commingsoon_list : RecyclerView
    lateinit var comingsoon_adapter : ComingsoonAdapter
    lateinit var comingsoon_imageButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comingsoon_list)


        commingsoon_list = findViewById(R.id.comingsoon_list)
        comingsoon_imageButton = findViewById(R.id.comingsoon_imageButton)

        val search_data : EditText = findViewById(R.id.comingsoonList_searchView)
        val nulltext : TextView = findViewById(R.id.nulltext2)

        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(this)
        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        comingsoon_adapter = ComingsoonAdapter(user_key)
        commingsoon_list.adapter = comingsoon_adapter
        commingsoon_list.layoutManager = LinearLayoutManager(applicationContext)

        var Search = search_data.text.toString()

        comingsoon_imageButton.setOnClickListener {

            if (!user_key.isNullOrEmpty()){
                val intent = Intent(this@ComingsoonListActivity, newWrite_WithActivity::class.java)
                startActivity(intent, null)
            }
            else {
                Toast.makeText(this@ComingsoonListActivity, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            }
        }


        commingsoon_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("page", "$page")

                val lastVisibleItemPosition =
                    (commingsoon_list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = commingsoon_list.adapter?.itemCount
                if (lastVisibleItemPosition + 1 == itemTotalCount){

                    retrofit_call()
                    page++
                }

            }
        })

        search_data.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Search = search_data.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        search_data.setOnEditorActionListener { _, i, _ ->

            var handled = false
            if (i == EditorInfo.IME_ACTION_SEARCH){

                call?.get_search_area2(Search)?.enqueue(object : Callback<WithClosingDTO>{
                    override fun onResponse(call: Call<WithClosingDTO>, response: Response<WithClosingDTO>) {
                        if (response.isSuccessful){
                            Search = null.toString()
                            var result : WithClosingDTO? = response.body()
                            val dataList = result!!.closing_data

                            if (result.closing_data.size == 0) {
                                Log.d("asdfg", "${result.closing_data.size}")

                                val toast = Toast.makeText(this@ComingsoonListActivity, "검색된 결과가 없습니다", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()

                            } else {

                                comingsoon_adapter.clearData()
                                comingsoon_adapter.addPosts(dataList)

                                handled = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<WithClosingDTO>, t: Throwable) {

                    }

                })
            }
            handled
        }


    }

    override fun onResume() {

        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }


        super.onResume()
        comingsoon_adapter.clearData()
        comingsoon_adapter.refreshData()

        page = 0

        retrofit_call()

        page++

    }


    fun retrofit_call(){
        call?.get_cloging_data(page, user_key)?.enqueue(object : Callback<WithClosingDTO>{
            override fun onResponse(
                call: Call<WithClosingDTO>,
                response: Response<WithClosingDTO>
            ) {
                if(response.isSuccessful){

                    val response : WithClosingDTO? = response.body()

                    Log.d("response", "성공 ${response?.closing_data!!}")

                    val ClosingDataList = response?.closing_data

                    comingsoon_adapter.addPosts(ClosingDataList)


                } else{
                    Log.d("comingsoon", "실패")
                }
            }

            override fun onFailure(call: Call<WithClosingDTO>, t: Throwable) {
                Log.d("comingsoon", "응답오류" + t.message.toString())
            }

        })
    }
}