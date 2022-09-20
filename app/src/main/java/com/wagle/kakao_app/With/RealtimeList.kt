package com.wagle.kakao_app.With

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.WithRealtimeDTO
import com.wagle.kakao_app.DTO.data_list
import com.wagle.kakao_app.DTO.search_withDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.adapter.RealtimeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealtimeList : AppCompatActivity() {

    //페이징
    var page : Int = 0

    // retrofit
    val call by lazy { RetrofitClass.getInstance() }

    //리스트 & adapter
    lateinit var realtiem_list: RecyclerView
    lateinit var real_adapter : RealtimeAdapter

    // 본인 user_key
    lateinit var user_key : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }

        realtiem_list = findViewById(R.id.realtime_list)
        val search_data : EditText = findViewById(R.id.realtimeList_searchView)

        // 글 작성하는 버튼
        val realtime_imageButton: ImageButton = findViewById(R.id.realtime_imageButton)
        val nulltext : TextView = findViewById(R.id.nulltext)

        real_adapter = RealtimeAdapter(user_key)
        realtiem_list.adapter = real_adapter

        realtiem_list.layoutManager = LinearLayoutManager(applicationContext) //수직으로 스크롤 할 수 있음

        var Search = search_data.text.toString()

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

                call?.get_search_area(Search)?.enqueue(object : Callback<WithRealtimeDTO>{
                    override fun onResponse(call: Call<WithRealtimeDTO>, response: Response<WithRealtimeDTO>) {
                        if (response.isSuccessful){
                            Search = null.toString()
                            var result : WithRealtimeDTO? = response.body()
                            val dataList = result!!.real_time_data


                            if (result.real_time_data.size == 0) {

                                val toast = Toast.makeText(this@RealtimeList, "검색된 결과가 없습니다", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()

                            } else {

                                nulltext.text = ""
                                real_adapter.clearData()
                                real_adapter.addPosts(dataList)

                                handled = true

                            }
                        }
                    }

                    override fun onFailure(call: Call<WithRealtimeDTO>, t: Throwable) {

                    }

                })
            }
            handled
        }

//        realtiem_list.layoutManager = StaggeredGridLayoutManager(2, 1)

        realtime_imageButton.setOnClickListener {

            if (!user_key.isNullOrEmpty()){

                val intent = Intent(this@RealtimeList, newWrite_WithActivity::class.java)
                startActivity(intent, null)
            } else {
                Toast.makeText(this@RealtimeList, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        realtiem_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("page", "$page")

                //staggerd 임
//                val lastItems = IntArray(2)
//                (realtiem_list.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(lastItems)
//                val lastItem = Math.max(lastItems[0], lastItems[1])
//                val itemTotalCount = realtiem_list.adapter?.itemCount
//                if(lastItem + 1 == itemTotalCount) {
//
//                }

                val lastVisibleItemPosition =
                    (realtiem_list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = realtiem_list.adapter?.itemCount
                if (lastVisibleItemPosition + 1 == itemTotalCount) {

//                    Toast.makeText(
//                        this@RealtimeList,
//                        "마지막 글입니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()

                    retrofit_call()
                    page++

                }
            }
        })

    }

    override fun onResume() {
        super.onResume()

        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }
        
        real_adapter.clearData()
        real_adapter.refreshData()

        page = 0

        retrofit_call()
        page++

    }


    fun retrofit_call(){
        call?.get_realTime_data(page, user_key)?.enqueue(object : Callback<WithRealtimeDTO> {
            override fun onResponse(
                call: Call<WithRealtimeDTO>, response: Response<WithRealtimeDTO>
            ) {
                if (response.isSuccessful) {
                    val response: WithRealtimeDTO? = response.body()
                    Log.d("response", "성공 ${response?.real_time_data!!}")

                    val RealtimeDataList = response?.real_time_data

                    real_adapter.addPosts(RealtimeDataList)

                } else {
                    Log.d("response", "실패")
                }
            }

            override fun onFailure(call: Call<WithRealtimeDTO>, t: Throwable) {

                Log.d("response", "응답 에러")
            }

        })
    }
}
