package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. รับค่าที่แถมมากับ Intent
        val message = intent.getStringExtra("EXTRA_MSG") ?: "No Message Received"

        setContent {
            MaterialTheme {
                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE3F2FD)) // สีฟ้าอ่อนให้ดูแตกต่างจากหน้าหลัก
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("This is Detail Activity", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // แสดงข้อความที่ได้รับ
                            Text("Message: $message", style = MaterialTheme.typography.bodyLarge)
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            // 3. ปุ่มกดเพื่อปิดหน้าจอ (finish)
                            Button(onClick = {
                                finish()
                                // เลื่อนลงเมื่อปิด
                                @Suppress("DEPRECATION")
                                overridePendingTransition(R.anim.stay, R.anim.slide_out_down)
                            }) {
                                Text("Close Activity")
                            }
                        }
                    }
                }
            }
        }
    }

    // กรณีที่ผู้ใช้กดปุ่ม Back System ก็ให้หน้าจอสไลด์กลับลงมาเช่นเดียวกัน
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down)
    }
}

