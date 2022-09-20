package com.wagle.kakao_app.With

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wagle.kakao_app.DTO.SearchResultDTO
import com.wagle.kakao_app.DTO.data_list
import com.wagle.kakao_app.DTO.search_withDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import com.wagle.kakao_app.With.adapter.with_search_adapter
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class With_searchPageActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var withSearchAdapter: with_search_adapter
    lateinit var user_key : String

    val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_search_page)

        val setText : TextView = findViewById(R.id.NullText)
        val search_date : EditText = findViewById(R.id.search_mate)
        recyclerView = findViewById(R.id.with_recyclerView)

        withSearchAdapter = with_search_adapter()
        recyclerView.adapter = withSearchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        user_key = MySharedPreferences.getUserKey(this)

        // 유저 키 값을 호출해온다 로그인 상태가 아니라면 토큰을 보내지 않아 활성화가 되지 않는다
        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        var Search = search_date.text.toString()
        Log.d("search_with", Search)

        search_date.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Search = search_date.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        search_date.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_SEARCH) {

                call?.get_search_user(Search)?.enqueue(object : retrofit2.Callback<search_withDTO>{
                    override fun onResponse(call: Call<search_withDTO>, response: Response<search_withDTO>) {
                        if (response.isSuccessful){
                            Search = null.toString()
                            var result : search_withDTO? = response.body()
                            val dataList = result!!.with_searchResult

                            if (result!!.with_searchResult.size == 0) {
                                withSearchAdapter.clearData()
                                withSearchAdapter.addPosts(dataList)
                                setText.text = "검색된 결과가 없습니다."
                            } else {

                                setText.text = ""
                                withSearchAdapter.clearData()
                                withSearchAdapter.addPosts(dataList)

                                handled = true


                            }
                        }
                    }

                    override fun onFailure(call: Call<search_withDTO>, t: Throwable) {

                    }

                })
            }
            handled
        }
    }
}

