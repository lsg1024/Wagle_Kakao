package com.wagle.kakao_app.Chatting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.wagle.kakao_app.Interface.MySharedPreferences
import com.wagle.kakao_app.R
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

class ChattingRoom : AppCompatActivity() {

    lateinit var mSocket: io.socket.client.Socket
    lateinit var room_adapter : ChattingRoomAdapter

    //xml에서 가져온 id
    lateinit var back_btn : ImageButton
    lateinit var room_title : TextView
    lateinit var room_info : ImageButton
    lateinit var context_list : RecyclerView
    lateinit var input : EditText
    lateinit var send_button : ImageView


    // chat_list에서 넘어오는 키값 (user_key, room_key)
    lateinit var user_key : String
    private var room_key : Int? = null


    // Gson
    private val gson = Gson()

    // 채팅에서 이미지 보낼때
//    private val SELECT_IMAGE = 100



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_room)


        // 본인 user_key
        user_key = MySharedPreferences.getUserKey(this)

        if (user_key.isNullOrEmpty()){
            Log.d("key_data", "key_data 없음")
        } else {
            Log.d("key_data", "응답 $user_key")
        }


        // chat_list에서 보내는 room_key 사용
        val room_key = intent.getIntExtra("room_key", 1)
        Log.d("chatroom", "성공 $room_key")
        val post_key = intent.getIntExtra("post_key", 1)
        val title = intent.getStringExtra("title")
        val type = intent.getIntExtra("type", 0)

        val joinEmit = joinData(room_key, post_key, title!!, type, user_key)

        back_btn = findViewById(R.id.back_btn)
        room_title = findViewById(R.id.room_title)
        room_info = findViewById(R.id.room_info)
        context_list = findViewById(R.id.context_list)
        input = findViewById(R.id.input)
        send_button = findViewById(R.id.send_button)


        room_title.text = title

        try {
            mSocket = IO.socket("http://oceanit.synology.me:8001/")
            Log.d("SOCKET", "Connection success : " + mSocket)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }


        room_adapter = ChattingRoomAdapter()
        context_list.adapter = room_adapter

        context_list.layoutManager = LinearLayoutManager(applicationContext)

//        send_button.setOnClickListener(sendMessage())

        // 연결
        mSocket.connect()

        // 연결 성공 시 room 입장
        mSocket.on(Socket.EVENT_CONNECT) { args: Array<Any?>? ->
            mSocket.emit("req_join_room", gson.toJson(joinData(room_key, post_key, title!!, type, user_key)))
            Log.d("socket", "입장")
        }

        // 채팅 입력
        send_button.setOnClickListener {
            val roomData = RoomData(room_key, user_key, input.text.toString())
            mSocket.emit("req_room_message", gson.toJson(RoomData(room_key, user_key, input.text.toString())))
            Log.d("socket", "입력")

            input.setText("")
        }

        // 채팅 표시
        mSocket.on("noti_room_message", Emitter.Listener { args  ->
            Log.d("socket", "출력 $args")

            val data = gson.fromJson(args[0].toString(), ChatModel::class.java)

            Log.d("socket", "출력 $data")

            if(data.user_key.toString() == user_key){
                data.type = 2
            } else{
                data.type = 1
            }

            runOnUiThread(Runnable(){
                room_adapter.addItem(data)
            })
        })
//            room_adapter.addItem(data)
//            addChat(data)

//        mSocket.emit("")

        back_btn.setOnClickListener {
            mSocket.disconnect()
            finish()
        }


        room_info.setOnClickListener {
            mSocket.emit("exit_room", gson.toJson(RoomData(room_key, user_key, input.text.toString())))

            mSocket.disconnect()
            finish()
        }

    }

    fun init() {



    }


//    val pofile_image: String,
//    val name : String,
//    val script: String,
//    val date_time: String,
//    val type: Int


    // 리사이클러뷰에 채팅 추가가
//    fun addChat(data: ChatModel) {
//        runOnUiThread {
//            if (data.type.equals("ENTER") || data.type.equals("LEFT")) {
//                room_adapter.addItem(data)
//                context_list.scrollToPosition(room_adapter.getItemCount() - 1)
//            } else {
//                room_adapter.addItem()
//                context_list.scrollToPosition(room_adapter.getItemCount() - 1)
//            }
//        }
//    }
//
//    fun sendMessage() {
//        mSocket.emit("req_room_message", gson.toJson(
//            ChatModel(
//                "MESSAGE",
//                script = "",
//                user_key,
//                input.getText().toString(),
//                System.currentTimeMillis()
//            )
//        )
//        )
//    }
//
//    // 방에서 나갈 때
//    override fun onDestroy() {
//        super.onDestroy()
//        mSocket.emit("exit_room", gson.toJson(RoomData(room_key, user_key)))
//    }


}

