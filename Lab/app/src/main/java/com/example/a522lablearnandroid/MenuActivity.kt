package com.example.a522lablearnandroid
import java.util.Properties
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a522lablearnandroid.architecture.mvc.MvcCounterActivity
import com.example.a522lablearnandroid.architecture.mvi.MviCounterActivity
import com.example.a522lablearnandroid.architecture.mvvm.MvvmCounterActivity
class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Log.v("MyTag", "Verbose: ข้อมูลยิบย่อย (เช่น ค่าแกน XYZ จาก Sensor ทุกๆ มิลลิวินาที)")
        Log.d("MyTag", "Debug: ข้อมูลไว้หาบั๊ก (เช่น ค่า ID ที่ดึงมาจาก Database = 123)")
        Log.i("MyTag", "Info: แจ้งสถานะทั่วไป (เช่น โหลดข้อมูล API สำเร็จแล้ว)")
        Log.w("MyTag", "Warn: เตือนว่าแปลกๆ นะ (เช่น โหลดภาพไม่ขึ้น เลยใช้ภาพ Default แทน)")
        Log.e("MyTag", "Error: พังแล้วจ้า (เช่น catch Exception ได้ หรือ API ร่วง)")

        setContent {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, RPGCardActivity::class.java))
                }) {
                    Text("RPGCardActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, PokedexActivity::class.java))
                }) {
                    Text("PokedexActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, LifeCycleComposeActivity::class.java))
                }) {
                    Text("LifeCycleComposeActivity")
                }

                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, MvvmCounterActivity::class.java))
                }) {
                    Text("MvvmCounterActivity")
                }

                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, MviCounterActivity::class.java))
                }) {
                    Text("MviCounterActivity")
                }

                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, CreateCopyActivity::class.java))
                }) {
                    Text("CreateCopyActivity")
                }

                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java))
                }) {
                    Text("SharedPreferencesActivity")
                }
                
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, ImagePickerActivity::class.java))
                }) {
                    Text("ImagePickerActivity")
                }
                
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SensorMvvmActivity::class.java))
                }) {
                    Text("SensorMvvmActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part9Activity::class.java))
                }) {
                    Text("Part 9 (Collapsing Toolbar)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, ResponsiveProfileActivity::class.java))
                }) {
                    Text("Responsive Profile")
                }
                Button(onClick = {
                    val intent = Intent(this@MenuActivity, DetailActivity::class.java)
                    intent.putExtra("EXTRA_MSG", "Hello from Menu!")
                    // 2. ใช้คำสั่งจัดการ Transition อย่าง ActivityOptionsCompat
                    val options = androidx.core.app.ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity,
                        R.anim.slide_in_up,
                        R.anim.stay
                    )
                    startActivity(intent, options.toBundle())
                }) {
                    Text("Activity Transition (Slide Up)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, WebViewActivity::class.java))
                }) {
                    Text("WebView Compose")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SnackbarEffectActivity::class.java))
                }) {
                    Text("Snackbar Side Effect")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, TodoSwipeActivity::class.java))
                }) {
                    Text("Swipe To-Do List")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, DonutChartActivity::class.java))
                }) {
                    Text("Donut Chart Canvas")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part2Activity::class.java))
                }) {
                    Text("Part 2 Activity (Complex list)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, LikeButtonActivity::class.java))
                }) {
                    Text("LikeButtonScreen")
                }
            }
        }
    }
}

// check in 24 feb
