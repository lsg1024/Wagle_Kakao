package com.wagle.kakao_app.Interface

import com.wagle.finaltest.DTO.WithFragmentDTO
import com.wagle.kakao_app.DTO.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface AuthRetrofitInterface {


    //   ************************************  메인  **************************************    //

    @GET("ai/userPick")
    fun get_waglePick() : Call<WaglePickDTO>


    //   ************************************  관광 api  **************************************    //
    @GET("locationBasedList?serviceKey=OaCIv5TQbHkuXUkE7nqvYHFjRsDUz03S9qAGRF1zAMCf9dfG3M2X6gRp2Dy9cVmpHtcVjrKLemWyDB%2BQcAWDLg%3D%3D")
    fun koreaAPI(
//        @Query("ServiceKey") ServiceKey : String,
        @Query("numOfRows") numOfRows : Int,
        @Query("pageNo") pageNo : Int,
        @Query("MobileOS") MobileOS : String,
        @Query("MobileApp") MobileApp : String,
        @Query("_type") _type : String,
        @Query("listYN") listYN : String,
        @Query("arrange") arrange : String,
        @Query("contentTypeId") contentTypeId : String,
        @Query("mapX") mapX : String,
        @Query("mapY") mapY : String,
        @Query("radius") radius : String,
    ): Call<koreaApi>

    //

    @POST("user/userCheck")
    fun checkToken(
        @Header("accessToken") accessToken: String?,
    ) : Call<tokenDTO>

    @POST("user/signUp")
    fun sendToken(
        @Header("accessToken") accessToken: String?,
    ) : Call<tokenDTO>

    // 방명록 메인 랭크
    @GET("guestPlace/rank")
    fun GetlLankplace(
        @Header("user_key") user_key: String,
    ): Call<GuestRankDTO>

    @GET("guestPlace/read/{gp_key}")
    fun GetDetailResult(@Path("gp_key") gp_key: Int): Call<user_placeDTO>

    @GET("/guestPlace/read/{gp_key}")
    fun GetLankResult(
        @Header("user_key") user_key: String,
        @Path("gp_key") gp_key: Int,
    ):Call<user_placeDTO>

    @Multipart
    @POST("guestPlace/create")
    fun PostCreatePlace(
        @Header("user_key") user_key: String,
        @Part("place") place: String,
        @Part("address") address: String,
        @Part("des") des: String,
        @Part img: MultipartBody.Part?,
    ): Call<ResponsePlace>

    @Multipart
    @POST("guestBook/create")
    fun PostCreateBook(
        @Header("user_key") user_key: String,
        @Part("gp_key") gp_key : Int,
        @Part("comment") comment: String,
        @Part img: MultipartBody.Part?,
    ): Call<comment_response>

//    @Multipart
//    @POST("/user/profile")
//    fun profile(
//        @Part profile: MultipartBody.Part?,
//        @Header("token") token: String?, ): Call<ProfileResponse?>?


    @POST("guestPlace/update/heart")
    fun PostHeartupdate(@Header("user_key") user_key: String, @Body userheartDTO: userHeartDTO) : Call<PostUserHeart>

    @POST("guestBook/update/heart")
    fun CommentHeartupdate(@Header("user_key") user_key: String, @Body commentHeartDTO : commentHeartDTO) : Call<CommentHeart>

    // 검색창에 검색어가 포함되면 검색된다
    @GET("guestPlace/search?")
    fun GetSearchResult(
        @Header("user_key") user_key: String,
        @Query("search") search: String,
    ): Call<SearchResultDTO>

    @GET("guestBook/search?")
    fun GetSearchPed(
        @Header("user_key") user_key: String,
        @Query("search") search: String,
    ): Call<SearchResultDTO>

    @GET("guestBook/search?")
    fun GetPed(
        @Header("user_key") user_key: String,
        @Query("page") page: Int
    ) : Call<SearchResultDTO>

    @GET("guestBook/read/{gb_key}")
    // Path는 {} 안에 있는 값을 동적으로 변환을 준다
    // {} 있는 값과 변수 이름이 같아야 한다다
    fun GetRead_detail_Result(
        @Path("gb_key") gb_key: Int,
    ): Call<user_placeDTO>

    @GET("guestBook/read/list/{gb_key}?")
    fun GetReadComment(
        @Header("user_key") user_key: String,
        @Path("gb_key") gb_key: Int,
        @Query("page") page: Int,
    ) : Call<user_placeDTO>

    //   ************************************  채팅  **************************************    //

    // 채팅 리스트 목록
    @GET("chat/chat_list")
    fun get_chatList(@Header("user_key") user_key: String) : Call<ChatListDTO>

    // 채팅방
    @GET("chat/chat_list_each/{room_key}")
    fun get_chat_list_each(@Path("room_key") room_key : Int) : Call<ChatDTO>




    //   ************************************  알림  **************************************    //

    @GET("alarm/alarm_main/{user_key}")
    fun get_alarm(
        @Path("user_key") user_key : Int) : Call<AlarmDTO>

    //   ************************************  동행  **************************************    //

    // 검색창

    // guest가 참여하기 버튼을 눌렀을 때
    @GET("accompany/companionPostC_chat/{post_key}")
    fun get_guestButton(@Path("post_key") post_key: Int, @Header("user_key")user_key: String) : Call<WithGuestpParticipateDTO>


    // 동행 글 작성
    @POST("accompany/companionPostC")
    fun withPost_create(@Header("user_key")user_key: String, @Body withpPostCreateDTO: WithPostCreateDTO) : Call<PostResult>

    // 글 작성시 채팅방 생성
    @GET("accompany/host_accompany_chat/{post_key}")
    fun get_chat_create(@Path("post_key") post_key: Int, @Header("user_key")user_key: String)

    // 유저 검색
    @GET("/accompany/companionPost_search_user?")
    fun get_search_user(@Query("search_user") search : String) : Call<search_withDTO>

    //
    @GET("/accompany/companionPost_search_area?")
    fun get_search_area(@Query("search_area") search: String) : Call<WithRealtimeDTO>

    //
    @GET("/accompany/companionPost_search_area?")
    fun get_search_area2(@Query("search_area") search: String) : Call<WithClosingDTO>

    //동행 main
    @GET("accompany/accompany_main")
    fun get_withMain() : Call<WithFragmentDTO>


    // 동행 최신 게시글
    @GET("accompany/companionPostR_A_real_time")
    fun get_realTime_data(
        @Query("page") page : Int,
        @Header("user_key") user_key: String
    ) : Call<WithRealtimeDTO>


    // 동행 곧 마감 게시글
    @GET("accompany/companionPostR_A_closing")
    fun get_cloging_data(
        @Query("page") page: Int,
        @Header("user_key") user_key: String
    ) : Call<WithClosingDTO>


    // 글 자세히 볼 때
    @GET("accompany/companionPostR/{post_key}")
    fun get_postdetail(@Path("post_key") post_key : Int, @Header("user_key")user_key: String) : Call<PostDetailDTO>


    // 동행 글의 호스트가 마감하기 버튼을 눌렀을 때
    @POST("accompany/companionDeadline_Btn/{post_key}")
    fun post_postDetail_end(@Path("post_key") post_key: Int, @Header("user_key")user_key: String) : Call<WithEndButtonDTO>



    //   ************************************  마이페이지  **************************************   //

    @GET("guestBook/read/myList/{user_key}?")
    fun GetMyGuestbook(
        @Path("user_key") user_key: String,
        @Query("page") page: Int,
    ) : Call<SearchResultDTO>

    @POST("mypage/first_login_userInfo")
    fun PostUserInfo(
        @Header("user_key") user_key : String,
        @Body UserInfoDTO : UserInfoDTO
    ) : Call<UserInfoResponse>

    // 마이페이지 상단 - 프로필
    @GET("mypage/show_me")
    fun GetMyProfile(
        @Header("user_key") user_key : String,
    ) : Call<MyProfileDTO>

    // 개인정보 수정
    @POST("mypage/profile_modify")
    fun PostprofileChange(
        @Header("user_key") user_key : String,
        @Body UserInfoDTO : UserInfoDTO
    ) : Call<UserInfoResponse>

    @GET("deco/show_my_deco")
    fun GetMyDeco(
        @Header("user_key") user_key: String
    ) : Call<DecorationDTO>

    @POST("mypage/deco_index")
    fun PostDecoIndex(
        @Header("user_key") user_key: String,
        @Body SaveDecoIndexDTO: SaveDecoIndexDTO,
    ) : Call<SaveDecoResponse>

    @GET("mypage/select_my_deco")
    fun GetMyDecoImage(
        @Header("user_key") user_key: String
    ) : Call<ShowMyDecoDTO>

    //   ************************************  짝꿍  **************************************    //

    // 짝꿍 list 불러오기
    @GET("pair/pair_list")
    fun getPairList(@Header("user_key") user_key: String) : Call<PairListDTO>

    // 짝꿍 QR로 활성화 시키기
    @POST("pair/qr_check/{post_key}/{qr}")
    fun post_QRCheck(
        @Path("post_key") post_key: Int,
        @Path("qr") qr : Int
    ) : Call<QRPostDTO>



    //   ************************************  신고  **************************************    //
    @GET("report/guestBook")
    fun Get_guestBookReport(
        @Query("user_key") user_key: String,
        @Query("Xuser_key") Xuser_key : Int,
        @Query("gb_key") gb_key: Int,
        @Query("des") des : String

    ) : Call <reportDTO>

    @GET("report/accompany")
    fun Get_AccompanyReport(
        @Query("user_key") user_key : String,
        @Query("Xuser_key") Xuser_key : Int,
        @Query("post_key") post_key : Int,
        @Query("des") des : String

    ) : Call<reportDTO>


}

