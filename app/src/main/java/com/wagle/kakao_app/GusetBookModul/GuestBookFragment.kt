package com.wagle.kakao_app.GusetBookModul

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingList
import com.wagle.kakao_app.DTO.GuestRankDTO
import com.wagle.kakao_app.DTO.SearchResultDTO
import com.wagle.kakao_app.GuestBook.LankAdapter
import com.wagle.kakao_app.GuestBook.SearchPackage.PedAdapter
import com.wagle.kakao_app.GuestBook.SearchPackage.SearchActivity
import com.wagle.kakao_app.Home.MainActivity
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.Pair.Pair_MainActivity
import com.wagle.kakao_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestBookFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var mainActivity: MainActivity
    lateinit var lankRecyclerView: RecyclerView
    lateinit var pedRecyclerView: RecyclerView
    lateinit var mainSearch: TextView
    lateinit var createPlace: TextView
    lateinit var user_key : String
    lateinit var ped_Adapter : PedAdapter
    private var page = 0

    val result by lazy { RetrofitClass.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_guest_book, container, false)
        mainActivity = context as MainActivity

        lankRecyclerView = view.findViewById(R.id.rank_recyclerView)
        pedRecyclerView = view.findViewById(R.id.ped_recyclerview)
        mainSearch = view.findViewById(R.id.main_searchView)
        createPlace = view.findViewById(R.id.create_place)

        ped_Adapter = PedAdapter()
        pedRecyclerView.adapter = ped_Adapter

        pedRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        user_key = MySharedPreferences.getUserKey(mainActivity)

        if (MySharedPreferences.getUserKey(mainActivity).isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 ${MySharedPreferences.getUserKey(mainActivity)}")
        }

        pedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItems = IntArray(2)
                (pedRecyclerView.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(lastItems)
                val lastItem = Math.max(lastItems[0], lastItems[1])
                val itemTotalCount = pedRecyclerView.adapter?.itemCount
                if(lastItem + 1 == itemTotalCount) {
                    ped_retrofit()
                    page++
                }
            }
        })

        val wifiButton : ImageButton = view.findViewById(R.id.imageButton2)
        val notificationButton : ImageButton = view.findViewById(R.id.imageButton3)
        val chattingButton : ImageButton = view.findViewById(R.id.imageButton1)

        wifiButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, Pair_MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }

        }

        notificationButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, AlarmList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        chattingButton.setOnClickListener {
            if (!user_key.isNullOrEmpty()){
                val intent = Intent(context, ChattingList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    override fun onStart() {
        super.onStart()

        createPlace.setOnClickListener {
            if (!user_key.isNullOrEmpty()) {
                val intent = Intent(context, Create_place::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(context, "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show()
            }
        }


        mainSearch.setOnClickListener {
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        ped_Adapter.clearData()
        ped_Adapter.refreshData()

        page = 0

        ped_retrofit()

        page++

        onRefresh()
    }

    override fun onRefresh() {
        result?.GetlLankplace(MySharedPreferences.getUserKey(mainActivity))?.enqueue(object : Callback<GuestRankDTO> {
            override fun onResponse(call: Call<GuestRankDTO>, response: Response<GuestRankDTO>) {
                if(response.isSuccessful){

                    val Response : GuestRankDTO? = response.body()

                    val DataList = Response?.result

                    if(DataList?.size != 0){
                        lankRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        lankRecyclerView.adapter = DataList?.let { LankAdapter(it) }
                    }

                } else{
                    Log.d("response", "실패")
                }
            }
            override fun onFailure(call: Call<GuestRankDTO>, t: Throwable) {
                Log.d("GetLankPlace", "응답 에러" + t.message.toString())
            }
        })

    }

    fun ped_retrofit() {
        result?.GetPed(user_key, page)?.enqueue(object : Callback<SearchResultDTO>{
            override fun onResponse(
                call: Call<SearchResultDTO>, response: Response<SearchResultDTO>,
            ) {
                if (response.isSuccessful) {

                    val response : SearchResultDTO? = response.body()

                    val dataList = response?.searchResult

                    if (dataList != null) {
                        ped_Adapter.addPosts(dataList)
                    }

//                    pedRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//                    pedRecyclerView.adapter = PedAdapter(DataList)

                } else {

                }
            }
            override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
            }
        })
    }
}



