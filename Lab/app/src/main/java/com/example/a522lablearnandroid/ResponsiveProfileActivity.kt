package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ResponsiveProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        ResponsiveProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileScreen() {
    // 1. เรียกใช้ BoxWithConstraints ซึ่งจะช่วยให้เราสามารถเข้าถึงค่า maxWidth, maxHeight ของพื้นที่หน้าจอเป๊ะๆ ได้
    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        
        // 2 & 3. เช็คเงื่อนไขตามข้อกำหนด (600.dp เป็นจุดตัดมาตรฐานสำหรับ มือถือ vs แท็บเล็ต/แนวนอน)
        if (maxWidth < 600.dp) {
            // หน้าจอมือถือแนวตั้ง (Portrait): เอาของวางซ้อนกันบนลงล่าง
            Column(modifier = Modifier.fillMaxSize()) {
                ProfilePicture(modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // ล็อกความสูง
                )
                Spacer(modifier = Modifier.height(16.dp))
                ProfileInfo(modifier = Modifier.fillMaxWidth())
            }
        } else {
            // หน้าจอแท็บเล็ต หรือมือถือแนวนอน (Landscape): เอาของวางข้างกันซ้ายขวา
            Row(modifier = Modifier.fillMaxSize()) {
                ProfilePicture(modifier = Modifier
                    .weight(1f) // แบ่งส่วนแบ่ง 1 ส่วน
                    .fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(16.dp))
                ProfileInfo(modifier = Modifier
                    .weight(2f) // แบ่งพื้นที่กว้างกว่าให้เป็น 2 ส่วน
                    .fillMaxHeight()
                )
            }
        }
    }
}

// Composable รูปโปรไฟล์สีเทาสมมติ
@Composable
fun ProfilePicture(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("รูปโปรไฟล์")
    }
}

// Composable ข้อมูล
@Composable
fun ProfileInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("ข้อมูลส่วนตัว", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "นี่คือหน้าต่างสาธิต Responsive Design ใน Jetpack Compose ของเบื้องหลังการสร้าง Layout แบบ Dynamic ครับ\n\n" +
                   "ตอนที่คุณหมุนหน้าจอแนวนอน โทรศัพท์มือถือก็จะขยายกว้างกว่า 600.dp แน่นอน ทำให้ระบบสลับไปใช้โครงสร้างบรรทัดแบบแถว (Row) ทันที!",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

