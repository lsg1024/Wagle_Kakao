package com.wagle.kakao_app.TrapLog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.wagle.kakao_app.DTO.koreaApi
import com.wagle.kakao_app.DTO.total_Data
import com.wagle.kakao_app.Home.MainActivity
import com.wagle.kakao_app.Interface.Retrofit_API
import com.wagle.kakao_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Response
import java.net.URL


val PERMISSIONS_REQUEST_CODE = 100
var REQUIRED_PERMISSIONS = arrayOf( Manifest.permission.ACCESS_FINE_LOCATION)


class TrapFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var mapView: MapView
    lateinit var mapViewContainer: ViewGroup
    lateinit var gps: Button
    lateinit var trapIf: Button
    var total = 0
    var uLatitude: Double = 0.0
    var uLongitude: Double = 0.0
    var boolean: Boolean = true
    lateinit var mMakerArray : String

    var marker_address : HashMap<String, String> = HashMap()
    var marker_img : HashMap<String, String> = HashMap()

    lateinit var url_data : String

    val call by lazy { Retrofit_API.getInstance() }
//    lateinit var url : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_trap, container, false)
        // 초기화는 뷰 쪽에서 해줘야됨 이유는 찾아봐야함
        mainActivity = context as MainActivity
        mapViewContainer = view.findViewById(R.id.map_view) as ViewGroup
        gps = view.findViewById(R.id.button4)
        trapIf = view.findViewById(R.id.trapIf)

        // 스토리북 저장하는 bottomSheetFragment 따로 호출해서 만들기

        return view
    }

    override fun onStart() {
        super.onStart()

        if (MapView.isMapTilePersistentCacheEnabled()) {
            MapView.setMapTilePersistentCacheEnabled(true)
        }

        gps.setOnClickListener{
            val permissionCheck = ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager = mainActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

//                val polyline = MapPolyline()
                if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null){
                    Toast.makeText(context, "현재위치를 조회할 수 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val userNowLocation: Location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!

                    uLatitude = userNowLocation.latitude
                    uLongitude = userNowLocation.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)

                    try {

                        if (boolean === true) {
                            !boolean
                            mapView.setZoomLevel(2, true)
                            mapView.currentLocationTrackingMode =
                                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                            mapView.setMapCenterPoint(uNowPosition, true)

                            Toast.makeText(mainActivity, "현재 위치를 표시합니다.", Toast.LENGTH_SHORT).show()
                            boolean = false

                        } else {
                            mapView.currentLocationTrackingMode =
                                MapView.CurrentLocationTrackingMode.TrackingModeOff
                            Toast.makeText(mainActivity, "현재 위치 고정을 해제합니다.", Toast.LENGTH_SHORT).show()
                            boolean = true
                        }


                    } catch (e: NullPointerException) {
                        Log.e("LOCATION_ERROR", e.toString())
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ActivityCompat.finishAffinity(mainActivity)
                        } else {
                            ActivityCompat.finishAffinity(mainActivity)
                        }


                        val intent = Intent(mainActivity, TrapFragment::class.java)
                        startActivity(intent)
                        System.exit(0)
                    }
                }
            } else {
                Toast.makeText(mainActivity, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    mainActivity,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

        val eventListener = context?.let { MarkerEventListener(it) }

        trapIf.setOnClickListener{

            Toast.makeText(context, "주변 관광지를 조회합니다.", Toast.LENGTH_SHORT).show()

            call?.koreaAPI(
                8,
                1,
                "AND",
                "Wagle",
                "json",
                "Y",
                "E",
                "12",
                uLongitude.toString(),
                uLatitude.toString(),
                "5000")!!
                .enqueue(object : retrofit2.Callback<koreaApi> {
                    override fun onResponse(call: Call<koreaApi>, response: Response<koreaApi>) {

                        val result: ArrayList<total_Data> = response.body()!!.response.body.items.item


//                        for (i in 0 until result.size) {
//                            val url_data = result[i].firstimage
//                            Log.d("api_image1", "url_data + ${url_data}")
//
//                        }

                        for (i in 0 until result.size) {

                            val marker = MapPOIItem()

                            uLatitude = result[i].mapy!!.toDouble()
                            uLongitude = result[i].mapx!!.toDouble()

                            mMakerArray = result[i].addr1!!.toString()

                            marker_address[result[i].title.toString()] = result[i].addr1.toString()


                            if (result[i].firstimage != ""){
//                                val url = URL("${result[i].firstimage}")
                                marker_img[result[i].title.toString()] = result[i].firstimage.toString()

                                Log.d("api_image", "${result[i].firstimage}")
                            }
                            else {
                                val url = URL("https://via.placeholder.com/250.png?text=image_Data%20Null")
                                marker_img[result[i].title.toString()] = url.toString()

                                Log.d("api_image", "${result[i].firstimage}")
                            }
//                            Log.d("imagesss", "$i , ${marker_img[result[i].title.toString()]}")


                            marker.itemName = result[i].title
                            marker.mapPoint = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                            marker.markerType = MapPOIItem.MarkerType.RedPin // 마커타입을 커스텀 마커로 지정.
                            marker.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
                            marker.isCustomImageAutoscale = false
                            marker.setCustomImageAnchor(0.5f, 1.0f)
                            mapView.addPOIItem(marker)

                        }


                    }
                    override fun onFailure(call: Call<koreaApi>, t: Throwable) {
                        Log.d("test", "${t.message.toString()}")
                    }
                })

//            Log.d("imagesss", "$marker_img")
            mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater, marker_address, marker_img))
            mapView.setPOIItemEventListener(eventListener)
        }
    }

    override fun onResume() {
        super.onResume()
        // 화면으로 복귀했을 경우 다시 뷰를 활성화 시킨다
        mapView = MapView(activity)
        mapViewContainer.addView(mapView)
    }


    override fun onPause() {
        super.onPause()
        // 다른 화면으로 넘어갔을 경우 종료 시키지 않고 멈춤 상태를 만든다
        mapViewContainer.removeView(mapView)
    }

    class CustomBalloonAdapter(inflater: LayoutInflater, private val addr: HashMap<String, String>, private val img: HashMap<String, String>): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.calloutballonadapter, null)
        val nameView: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val addressView : TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        val imgView : ImageView = mCalloutBalloon.findViewById(R.id.imageFile)

//        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)
//        val image_file : ImageView = mCalloutBalloon.findViewById(R.id.imageFile)


        @SuppressLint("CheckResult")
        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선


            var name = poiItem?.itemName

            // nameView.text = poiItem?.itemName // 해당 마커의 정보 이용 가능

            nameView.text = name
            addressView.text = addr[name]
            var imgUrl = img[name].toString()

            Log.d("imagesss", "$imgUrl")
            val requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
            requestOptions.skipMemoryCache(true)
            requestOptions.centerCrop()
            requestOptions.circleCrop()
            requestOptions.signature(ObjectKey(System.currentTimeMillis()))


            Glide.with(mCalloutBalloon.context)
                .asBitmap()
                .load(imgUrl)
                .apply(requestOptions)
                .centerCrop()
                .into(
                    object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imgView.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                    }
                })

            return mCalloutBalloon
        }


        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
//                address.text = "getPressedCalloutBalloon"
            return mCalloutBalloon
        }
    }

    class MarkerEventListener(val context: Context) : MapView.POIItemEventListener {

        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {

        }

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            buttonType: MapPOIItem.CalloutBalloonButtonType?,
        )
        {

        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

        }
    }
}