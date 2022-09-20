package com.wagle.kakao_app.MyPage

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.wagle.kakao_app.Alarm.AlarmList
import com.wagle.kakao_app.Chatting.ChattingList
import com.wagle.kakao_app.DTO.*
import com.wagle.kakao_app.Home.MainActivity
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.Interface.RetrofitClass
import com.wagle.kakao_app.MyPage.list.MyGuestBook.MyProfile_update
import com.wagle.kakao_app.MyPage.list.MyGuestbook
import com.wagle.kakao_app.MyPage.list.MyProfile_insert
import com.wagle.kakao_app.Pair.Pair_MainActivity
import com.wagle.kakao_app.R
import com.wagle.kakao_app.Terms_service
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    private val call by lazy { RetrofitClass.getInstance() }

    lateinit var user_key : String


    lateinit var profile_img :CircleImageView
    lateinit var profile_nickname : TextView
    lateinit var profile_mbti : TextView
    lateinit var profile_intro : TextView
    lateinit var profile_tag1 : TextView
    lateinit var profile_tag2 : TextView
    lateinit var profile_tag3 : TextView
    lateinit var profile_tag4 : TextView
    var deco_arr : ArrayList<Int> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        //var view = inflater.inflate(R.layout.fragment_my_page, container, false)

        user_key = MySharedPreferences.getUserKey(requireContext())

        val view = if (!user_key.isNullOrEmpty()) {
            inflater.inflate(R.layout.fragment_my_page_2, container, false)
        } else {
            inflater.inflate(R.layout.fragment_my_page, container, false)
        }

        deco_arr.add(R.drawable.deco_1)
        deco_arr.add(R.drawable.deco_1)
        deco_arr.add(R.drawable.deco_2)
        deco_arr.add(R.drawable.deco_3)
        deco_arr.add(R.drawable.deco_4)
        deco_arr.add(R.drawable.deco_5)
        deco_arr.add(R.drawable.deco_6)
        deco_arr.add(R.drawable.deco_7)
        deco_arr.add(R.drawable.deco_8)
        deco_arr.add(R.drawable.deco_9)
        deco_arr.add(R.drawable.deco_10)
        deco_arr.add(R.drawable.deco_11)
        deco_arr.add(R.drawable.deco_12)
        deco_arr.add(R.drawable.deco_13)
        deco_arr.add(R.drawable.deco_14)
        deco_arr.add(R.drawable.deco_15)


        // 로그인 전 **************************************************************************
        mainActivity = context as MainActivity

        // 로그인 버튼
        val kakao_login_button = view?.findViewById<ImageButton>(R.id.kakao_login_button)
        val no_login = view?.findViewById<ConstraintLayout>(R.id.no_login_layout)

        kakao_login_button?.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(view.context)) {
                UserApiClient.instance.loginWithKakaoAccount(view.context, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(view.context, callback = callback)
            }
        }

        val term_service : TextView = view.findViewById(R.id.term_service)
        term_service.setOnClickListener {
//                val intent = Intent(context as MainActivity, Terms_service::class.java)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://oceanit.synology.me:8001/admin/privacy")
            startActivity(intent)
        }

        no_login?.setOnClickListener {
            Toast.makeText(context, "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show()
        }



        // 로그인 후 ******************************************************************************

        if (!user_key.isNullOrEmpty()) {
            profile_img = view.findViewById<CircleImageView>(R.id.profile_img)
            profile_nickname = view.findViewById<TextView>(R.id.profile_nickname)
            profile_mbti = view.findViewById<TextView>(R.id.profile_mbti)
            profile_intro = view.findViewById<TextView>(R.id.profile_intro)
            profile_tag1 = view.findViewById<TextView>(R.id.profile_tag1)
            profile_tag2 = view.findViewById<TextView>(R.id.profile_tag2)
            profile_tag3 = view.findViewById<TextView>(R.id.profile_tag3)
            profile_tag4 = view.findViewById<TextView>(R.id.profile_tag4)

            profile_tag1.text = "+"
            profile_tag2.text = "+"
            profile_tag3.text = "+"
            profile_tag4.text = "+"


            val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
            seekBar?.isEnabled = false

            val my_guestBook_btn = view.findViewById<TextView>(R.id.my_guestBook)   // 내가 쓴 방명록
            val my_trace_btn = view.findViewById<TextView>(R.id.my_trace)           // 나의 여행기록
            val my_storybook_btn = view.findViewById<TextView>(R.id.my_storybook)   // 스토리 북
            val my_profile_update_btn =
                view.findViewById<TextView>(R.id.my_profile_update)  // 프로필 수정
            val my_notice_btn = view.findViewById<TextView>(R.id.my_notice)         // 고객센터
            val term_service = view.findViewById<TextView>(R.id.term_service)
            val logout_btn = view.findViewById<TextView>(R.id.logout)               // 로그아웃

            val wifiButton : ImageButton = view.findViewById(R.id.imageButton2)
            val notificationButton : ImageButton = view.findViewById(R.id.imageButton3)
            val chattingButton : ImageButton = view.findViewById(R.id.imageButton1)

            wifiButton.setOnClickListener {
                val intent = Intent(context, Pair_MainActivity::class.java)
                startActivity(intent)
            }

            notificationButton.setOnClickListener {
                val intent = Intent(context, AlarmList::class.java)
                startActivity(intent)
            }

            chattingButton.setOnClickListener {
                val intent = Intent(context, ChattingList::class.java)
                startActivity(intent)
            }

            my_guestBook_btn?.setOnClickListener {
                val intent = Intent(context, MyGuestbook::class.java)
                startActivity(intent)
            }

            my_trace_btn?.setOnClickListener {
                not_ready()
            }

            my_storybook_btn?.setOnClickListener {
//                val intent = Intent(context, MyStorybook::class.java)
//                startActivity(intent)
                not_ready()
            }

            my_profile_update_btn?.setOnClickListener {
                val intent = Intent(context, MyProfile_update::class.java)
                startActivity(intent)
            }

            my_notice_btn?.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                val address = arrayOf("knk7691@gmail.com")
                email.putExtra(Intent.EXTRA_EMAIL, address)
                email.putExtra(Intent.EXTRA_SUBJECT, "[Wagle] 제목")
                email.putExtra(Intent.EXTRA_TEXT, "수정, 삭제 기타 요청 사항을 입력해주세요\n* 이름과 이메일 필수 *")
                startActivity(email)
            }

            term_service?.setOnClickListener {
//                val intent = Intent(context as MainActivity, Terms_service::class.java)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse("http://oceanit.synology.me:8001/admin/privacy"))
                startActivity(intent)
            }

            logout_btn?.setOnClickListener {
                MySharedPreferences.removeKey(mainActivity)
                Toast.makeText(mainActivity, "로그아웃 성공", Toast.LENGTH_LONG).show()
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    } else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    }
                }
                // 앱 종료 후 재시작
                val packageManager = (context as MainActivity).packageManager
                val intent = packageManager.getLaunchIntentForPackage((context as MainActivity).packageName)
                val componentName = intent!!.component
                val mainIntent = Intent.makeRestartActivityTask(componentName)
                (context as MainActivity).startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }




        }

        Log.d("MyProfile111", "$user_key")

        if(!user_key.isNullOrEmpty()){
            getMyprofile()
        }

        // *********************************훈장 코드*************************************** //
        // 훈장 글씨 옆에 물음표 버튼 (훈장 설명)
        val full_decoraion_btn = view.findViewById<ImageView>(R.id.imageView6)

        full_decoraion_btn?.setOnClickListener {

        }

        // 로그인 상태에서 훈장 '+' 아이콘 눌렀을 때
        val first_decoration_btn = view.findViewById<ImageView>(R.id.dacoImage1)
        val second_decoration_btn = view.findViewById<ImageView>(R.id.dacoImage2)
        val third_decoration_btn = view.findViewById<ImageView>(R.id.dacoImage3)

        call?.GetMyDecoImage(user_key)?.enqueue(object : Callback<ShowMyDecoDTO> {

            override fun onResponse(
                call: Call<ShowMyDecoDTO>, response: Response<ShowMyDecoDTO>,
            ) {
                if (response.isSuccessful) {

                    val result: ShowMyDecoDTO? = response.body()

                    for(x in result!!.db_data) {
                        Log.d("x", x.toString())
                        if (x.deco_index == 1) {
                            first_decoration_btn.setImageResource(deco_arr[x.deco_key])
                        }
                        if (x.deco_index == 2) {
                            second_decoration_btn.setImageResource(deco_arr[x.deco_key])
                        }
                        if (x.deco_index == 3) {
                            third_decoration_btn.setImageResource(deco_arr[x.deco_key])
                        }
                    }

                } else{
                    Log.d("data", "err")
                }
            }
            override fun onFailure(call: Call<ShowMyDecoDTO>, t: Throwable) {
            }
        })

        var index : Int

        first_decoration_btn?.setOnClickListener {
            val dialog = DecorationCustomDialog(mainActivity)
            dialog.showDia(user_key)

            dialog.setOnClickedListener(object : DecorationCustomDialog.ButtonClickListener{
                override fun onClicked(deco: Int) {
                    index = deco
                    Log.d("test", "$index")

                    first_decoration_btn.setImageResource(deco_arr[index])

                    //DB index insert
                    DecoIndex(user_key, SaveDecoIndexDTO(index, 1))
                }
            })
        }

        second_decoration_btn?.setOnClickListener {
            val dialog = DecorationCustomDialog(mainActivity)
            dialog.showDia(user_key)

            dialog.setOnClickedListener(object : DecorationCustomDialog.ButtonClickListener{
                override fun onClicked(deco: Int) {
                    index = deco
                    Log.d("test", "$index")

                    second_decoration_btn.setImageResource(deco_arr[index])

                    //DB index insert
                    DecoIndex(user_key, SaveDecoIndexDTO(index, 2))
                }
            })
        }

        third_decoration_btn?.setOnClickListener {
            val dialog = DecorationCustomDialog(mainActivity)
            dialog.showDia(user_key)

            dialog.setOnClickedListener(object : DecorationCustomDialog.ButtonClickListener{
                override fun onClicked(deco: Int) {
                    index = deco
                    Log.d("test", "$index")

                    third_decoration_btn.setImageResource(deco_arr[index])

                    //DB index insert
                    DecoIndex(user_key, SaveDecoIndexDTO(index, 3))
                }
            })
        }


        return view
    }

    fun DecoIndex (user_key: String, SaveDecoIndexDTO: SaveDecoIndexDTO) {

        call?.PostDecoIndex(user_key, SaveDecoIndexDTO)?.enqueue(object : Callback<SaveDecoResponse> {

            override fun onResponse(
                call: Call<SaveDecoResponse>, response: Response<SaveDecoResponse>,
            ) {
                if (response.isSuccessful) {

                    val response : SaveDecoResponse? = response.body()
                    Log.d("test3", "$response")
                } else {
                    Log.d("test3", "오류")
                }
            }
            override fun onFailure(call: Call<SaveDecoResponse>, t: Throwable) {
                Log.d("test3", "${t.message.toString()}")
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (!user_key.isNullOrEmpty()){
            getMyprofile()
        }

    }

    fun not_ready(){
        Toast.makeText(context, "추후 업데이트될 예정입니다.", Toast.LENGTH_SHORT).show()
    }


    fun getMyprofile(){
        call?.GetMyProfile(user_key)?.enqueue(object : Callback<MyProfileDTO>{
            override fun onResponse(call: Call<MyProfileDTO>, response: Response<MyProfileDTO>) {
//                 val response: MyProfileDTO? = response.body()

                Log.d("MyProfile222", "${response.body()?.MyProfile}")

                Glide.with(this@MyPageFragment)
                    .load(response.body()?.MyProfile!![0].img)
                    .into(profile_img)

                profile_nickname.text = response.body()?.MyProfile!![0].nickname
                profile_mbti.text = response.body()?.MyProfile!![0].mbti
                profile_intro.text = response.body()?.MyProfile!![0].intro

                var tags = response.body()?.MyProfile!![0].tag.split(" ")
                for(i in tags.indices){
                    when (i) {
                        0 -> {
                            profile_tag1.text = tags[i]
                        }
                        1 -> {
                            profile_tag2.text = tags[i]
                        }
                        2 -> {
                            profile_tag3.text = tags[i]
                        }
                        3 -> {
                            profile_tag4.text = tags[i]
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MyProfileDTO>, t: Throwable) {
                Log.e("MyProfile", t.message.toString())
            }

        })
    }





    // 로그인 정보 확인
    fun tokenCheck(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(context, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(context, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(context, "로그인 성공 후 페이지 "::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
    }

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(context, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(context, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(context, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                        .show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(context, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(context, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(
                        context,
                        "설정이 올바르지 않음(android key hash)",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(context, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(context, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(context, "기타 에러", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (token != null) {

            val accessToken = token.accessToken
            Log.d("response_user", "성공 ${accessToken}")


            call?.checkToken(accessToken)?.enqueue(object : retrofit2.Callback<tokenDTO>{
                override fun onResponse(call: Call<tokenDTO>, response: Response<tokenDTO>) {
                    val response: tokenDTO? = response.body()

                    Log.d("login", "${response?.tokenResult!![0].login}")

                    val check = response.tokenResult[0].login

                    if (!check) {
                        // signUp()
                        val intent = Intent(context, MyProfile_insert::class.java)
                        intent.putExtra("accessToken", accessToken)
                        startActivity(intent)


                    } else if (check) {
                        val userKey = response.tokenResult[0].user_key
                        Log.d("response_user", "응답 $userKey")
                        MySharedPreferences.setUserKey(context as MainActivity, userKey)


                        // 앱 종료 후 재시작
                        val packageManager = (context as MainActivity).packageManager
                        val intent = packageManager.getLaunchIntentForPackage((context as MainActivity).packageName)
                        val componentName = intent!!.component
                        val mainIntent = Intent.makeRestartActivityTask(componentName)
                        (context as MainActivity).startActivity(mainIntent)
                        Runtime.getRuntime().exit(0)


                    }
                    else {
                        return tokenCheck()
                    }
                }

                override fun onFailure(call: Call<tokenDTO>, t: Throwable) {
                    Log.e("test", t.message.toString())

                    Toast.makeText(context, "서버 연결 오류", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}