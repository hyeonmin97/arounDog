package com.example.aroundog

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.aroundog.Service.Polyline
import com.example.aroundog.Service.WalkService
import com.example.aroundog.dto.WalkInfoDto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class WalkInfoActivity : AppCompatActivity() {
    val TAG = "WALKINFOACTIVITY"
    lateinit var walkDetailsDate:TextView
    lateinit var walkDetailsImageView:ImageView
    lateinit var walkInfoTime:TextView
    lateinit var walkInfoSecond:TextView
    lateinit var walkInfoDistance:TextView
    lateinit var floatingActionButton:FloatingActionButton
    lateinit var walkInfoBack:ImageButton
    lateinit var walkInfoDogs:LinearLayout
    lateinit var walkInfoNone:TextView
    lateinit var walkInfoButton: Button

    lateinit var walkInfoDto:WalkInfoDto

    companion object{
        var isDelete = MutableLiveData<Boolean>(false)
        var data = MutableLiveData<LongString>()
    }
    class LongString(var walkId: Long, var yearMonth: String)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_info)

        setView()

        var walkService = setRetrofit()


        var walkId = intent.getLongExtra("walkId", -1L)

        if (walkId != -1L) {
            getWalkInfo(walkService, walkId)
        } else {
            Toast.makeText(applicationContext, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        floatingActionButton.setOnClickListener {
            if (this::walkInfoDto.isInitialized) {
                var pathData = walkInfoDto.course
                var intent = Intent(applicationContext, WalkInfoMapActivity::class.java)
                intent.putExtra("pathData", pathData)
                startActivity(intent)
            }
        }

        //뒤로가기 버튼 리스너
        walkInfoBack.setOnClickListener {
            finish()
        }

        walkInfoButton.setOnClickListener {
            walkService.delete(walkId).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        if (response.body() == true) {
                            var str =
                                "" + walkInfoDto.startTime.year + "-" + walkInfoDto.startTime.monthValue
                            isDelete.postValue(true)
                            data.postValue(LongString(walkId, str))
                            Toast.makeText(applicationContext, "삭제 성공", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        Toast.makeText(applicationContext, "삭제 실패", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d(TAG, "실패", t)
                    Toast.makeText(applicationContext, "삭제 실패", Toast.LENGTH_SHORT).show()
                    finish()
                }

            })
        }
    }

    private fun getWalkInfo(
        walkService: WalkService,
        walkId: Long
    ) {
        //레트로핏
        //경로까지 다 들고와서 지도 이미지 한번 누르면 지도로 확인할 수 있게하면 될듯??
        walkService.getWalkInfo(walkId).enqueue(object : Callback<WalkInfoDto> {
            override fun onResponse(call: Call<WalkInfoDto>, response: Response<WalkInfoDto>) {
                if (response.isSuccessful) {
                    walkInfoDto = response.body()!!

                    //이미지 설정
                    var bitmap: Bitmap
                    var byteArray: ByteArray = Base64.decode(walkInfoDto.img, Base64.DEFAULT)
                    bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    //에러일경우
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(resources, R.drawable.error2)
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        walkDetailsImageView.setImageBitmap(bitmap)
                    }

                    var idNameMap = walkInfoDto.idNameMap
                    if (!idNameMap.isEmpty()) {
                        walkInfoNone.visibility = View.GONE

                        for (entry in idNameMap) {
                            var layout = LayoutInflater.from(applicationContext)
                                .inflate(R.layout.walking_dog, null)
                            var imageView = layout.findViewById<ImageView>(R.id.walkingImageView)
                            var textView = layout.findViewById<TextView>(R.id.walkingTV)
                            var path = BuildConfig.SERVER + "image/" + entry.key
                            Glide.with(applicationContext).load(path).override(100)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
                                .skipMemoryCache(true)// 메모리 캐시 저장 off
                                .error(R.drawable.error).into(imageView)
                            textView.text = entry.value
                            walkInfoDogs.addView(layout)
                        }
                    }




                    //텍스트뷰 설정
                    var start = walkInfoDto.startTime.format(DateTimeFormatter.ofPattern("a hh:mm"))
                    var end = walkInfoDto.endTime.format(DateTimeFormatter.ofPattern("a hh:mm"))
                    walkDetailsDate.text =
                        walkInfoDto.startTime.format(DateTimeFormatter.ofPattern("YY - MM - dd (E)"))
                    walkInfoTime.text = "$start  -  $end"
                    walkInfoSecond.text = String.format("%.1f 분", (walkInfoDto.second / 60.0))
                    walkInfoDistance.text = walkInfoDto.distance.toString() + " M"


                } else {
                    Log.d(TAG, "실패 ")
                }

            }

            override fun onFailure(call: Call<WalkInfoDto>, t: Throwable) {
                Log.d(TAG, "실패 ", t)
            }
        })
    }

    fun setView() {
        walkDetailsDate = findViewById(R.id.walkDetailsDate)
        walkInfoTime = findViewById(R.id.walkInfoTime)
        walkInfoSecond = findViewById(R.id.walkInfoSecond)
        walkInfoDistance = findViewById(R.id.walkInfoDistance)
        floatingActionButton = findViewById(R.id.floatingActionButton)
        walkDetailsImageView = findViewById(R.id.walkDetailsImageView)
        walkInfoBack = findViewById(R.id.walkInfoBack)
        walkInfoDogs = findViewById(R.id.walkInfoDogs)
        walkInfoNone = findViewById(R.id.walkInfoNone)
        walkInfoButton = findViewById(R.id.walkInfoButton)
    }
    private fun setRetrofit(): WalkService {
        var jsonLocalDateTimeDeserializer = object : JsonDeserializer<LocalDateTime> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDateTime {
                return LocalDateTime.parse(
                    json!!.asString,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                )
            }

        }
        var gson = GsonBuilder().registerTypeAdapter(
            LocalDateTime::class.java,
            jsonLocalDateTimeDeserializer
        ).create()


        var retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WalkService::class.java)
        return retrofit
    }

    override fun onDestroy() {
        super.onDestroy()
        isDelete.postValue(false)
    }
}