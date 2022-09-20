package com.wagle.kakao_app.GuestBook.SearchPackage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wagle.finaltest.GuestBook.SearchPackage.SearchAdapter
import com.wagle.kakao_app.DTO.SearchResultDTO
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    lateinit var recyclerView1 : RecyclerView
    lateinit var Search_Adapter : SearchAdapter
    lateinit var user_key : String
    lateinit var addText : TextView
    private var page = 0

    val call by lazy { RetrofitClass.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        addText = findViewById(R.id.addText)
        val Search_Data: EditText = findViewById(R.id.searchView)
        recyclerView1 = findViewById(R.id.recyclerView2)

        Search_Adapter = SearchAdapter()
        recyclerView1.adapter = Search_Adapter
        recyclerView1.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        user_key = MySharedPreferences.getUserKey(this)

        // 유저 키 값을 호출해온다 로그인 상태가 아니라면 토큰을 보내지 않아 활성화가 되지 않는다
        if (MySharedPreferences.getUserKey(this).isNullOrEmpty()) {
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(this)}")
        }

        var search = Search_Data.text.toString()
        Log.d("GetSearchResult", "$search")

        Search_Data.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search = Search_Data.text.toString()

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        Search_Data.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                call?.GetSearchPed(user_key, search)?.enqueue(object : Callback<SearchResultDTO> {
                    override fun onResponse(call: Call<SearchResultDTO>, response: Response<SearchResultDTO>) {
                        if (response.isSuccessful) {
                            Log.d("GetSearchResult", "$search")
                            search_place_data(search)
                            search = null.toString()
                            Log.d("GetSearchResult", "$search")
                            var result: SearchResultDTO? = response.body()
//                                Log.d("GetSearchResult", "검색 결과 성공 ${result?.searchResult!!}")


                            val DataList = result?.searchResult

                            if (DataList != null) {
                                Search_Adapter.addPosts(DataList)
                            }

                                handled = true
                            } else {
                                Log.d("response", "실패")
                            }
                        }

                        override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                            Log.d("response", "응답 에러" + t.message.toString())
                        }
                    })
            }
            handled
        }
    }

    // 검색 라우터 수정
    fun search_place_data(search : String) {
        call?.GetSearchResult(user_key, search)?.enqueue(object : Callback<SearchResultDTO>{
            override fun onResponse(call: Call<SearchResultDTO>, response: Response<SearchResultDTO>) {
                if (response.isSuccessful){
                    val result : SearchResultDTO? = response.body()

                    if (result?.searchResult?.size != 0){
                        addText.text = result!!.searchResult[0].place
                        addText.setOnClickListener {
                            val intent = Intent(this@SearchActivity, ImageClick_Actitivty::class.java)
                            intent.putExtra("search_result", result.searchResult[0].gp_key)
                            startActivity(intent)
                        }
                    } else {
                        addText.text = "검색 결과가 없습니다"
                    }
                }
            }

            override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                Log.d("response", "응답 에러" + t.message.toString())
            }

        })
    }

//        recyclerView1.addOnScrollListener(object :RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val lastItems = IntArray(2)
//
//                (recyclerView1.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(
//                    lastItems)
//                val lastItem = Math.max(lastItems[0], lastItems[1])
//                val itemTotalCount = recyclerView1.adapter?.itemCount
//                if (lastItem + 1 == itemTotalCount) {
//                }

//                Log.d("GetSearchResult", "$second_search")
//                search_data.setOnEditorActionListener { _, i, _ ->
//                    var handled = false
//                    if (i == EditorInfo.IME_ACTION_SEARCH) {
//                        call?.GetSearchResult(user_key, second_search)
//                            ?.enqueue(object : Callback<SearchResultDTO> {
//                                override fun onResponse(
//                                    call: Call<SearchResultDTO>,
//                                    response: Response<SearchResultDTO>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        second_search = null.toString()
//                                        Log.d("GetSearchResult", "$second_search")
//                                        var result: SearchResultDTO? = response.body()
//                                        Log.d("GetSearchResult",
//                                            "검색 결과 성공 ${result?.searchResult!!}")
//
//                                        if (result.searchResult.size != 0) {
//                                            addText.text = result.searchResult[0].place
//                                        } else {
//                                            addText.text = "검색결과를 찾을 수 없습니다."
//                                        }
//
//                                        val DataList = result.searchResult
//
//                                        Search_Adapter.addPosts(DataList)
//
//                                        handled = true
//                                    } else {
//                                        Log.d("response", "실패")
//                                    }
//                                }
//
//                                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
//                                    Log.d("response", "응답 에러" + t.message.toString())
//                                }
//                            })
//                    }
//                    handled
//                }
//                page++
//            }
//        })
//    }
//
//        Log.d("GetSearchResult", "두번쨰 검색 성공 $second_search")
//
//        if (frist_research != null) {
//            call?.GetSearchResult(user_key, frist_research)?.enqueue(object : retrofit2.Callback<SearchResultDTO> {
//
//                override fun onResponse(call: Call<SearchResultDTO>, response: Response<SearchResultDTO>) {
//
//                    if (response.isSuccessful) {
//                        frist_research = null
//
//                        var response : SearchResultDTO? = response.body()
//
//                        Log.d("GetSearchResult", "처음 검색 성공 ${response?.searchResult!!}")
//                        val search_size = response.searchResult.size
//
//                        if (search_size != 0) {
//                            addText.text = response.searchResult[0].place
//                            addText.setOnClickListener {
//                                val intent_main_data = Intent(this@SearchActivity, ImageClick_Actitivty::class.java)
//                                intent_main_data.putExtra("search_main_data", response.searchResult[0].gp_key)
//                                Log.d("GetSearchResult", "두번쨰 검색 성공 $intent_main_data")
//                                startActivity(intent_main_data)
//                            }
//                        } else {
//                            addText.text = "값을 불러오지 못했습니다"
//                        }
//                        val DataList = response.searchResult
//
//                        if (DataList != null) {
//                            Search_Adapter.addPosts(DataList)
//                        }
//
//                    } else {
//                        Log.d("response", "실패")
//                    }
//                }
//                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
//                    Log.d("GetSearch", "응답 에러" + t.message.toString())
//                }
//            })
//        }
//
//        recyclerView1.addOnScrollListener(object :RecyclerView.OnScrollListener(){
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val lastItems = IntArray(2)
//
//                (recyclerView1.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(
//                    lastItems)
//                val lastItem = Math.max(lastItems[0], lastItems[1])
//                val itemTotalCount = recyclerView1.adapter?.itemCount
//                if (lastItem + 1 == itemTotalCount) {
//
//                    search_data.setOnEditorActionListener { _, i, _ ->
//
//                        var handled = false
//
//                        Log.d("GetData", "$second_search")
//                        if (i == EditorInfo.IME_ACTION_SEARCH) {
//                            call?.GetSearchResult(user_key, second_search.toString())
//                                ?.enqueue(object : retrofit2.Callback<SearchResultDTO> {
//
//                                    override fun onResponse(
//                                        call: Call<SearchResultDTO>,
//                                        response: Response<SearchResultDTO>
//                                    ) {
//                                        if (response.isSuccessful) {
//
//                                            var result: SearchResultDTO? = response.body()
//
//                                            Log.d("GetSearchResult", "검색 결과 성공 ${result?.searchResult!!}")
//
//                                            if (result.searchResult.size != 0) {
//                                                addText.text = result.searchResult[0].place
//                                            } else {
//                                                addText.text = "검색 결과가 없습니다"
//                                            }
//
//                                            val DataList = result.searchResult
//
//                                            if (DataList != null) {
//                                                Search_Adapter.addPosts(DataList)
//                                            }
//
//                                            handled = true
//                                        } else {
//                                            Log.d("response", "실패")
//                                        }
//                                    }
//
//                                    override fun onFailure(
//                                        call: Call<SearchResultDTO>,
//                                        t: Throwable
//                                    ) {
//                                        Log.d("response", "응답 에러" + t.message.toString())
//                                    }
//                                })
//                        }
//                        handled
//                    }
//
//                    page++
//                }
//            }
//        })
//    }

//    override fun onResume() {
//        super.onResume()
//        Search_Adapter.clearData()
//        Search_Adapter.refreshData()
//
//        page = 0
//
//        page++
//
//    }

}