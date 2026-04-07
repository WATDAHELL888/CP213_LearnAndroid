package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CollapsingScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingScreen() {
    // 1. กำหนดพฤติกรรมการเลื่อน (Scroll Behavior) สำหรับ TopAppBar
    // exitUntilCollapsedScrollBehavior = เมื่อเลื่อนแถบจะหดเล็กลงจนถึงเนื้อที่ขั้นต่ำ แล้วหยุด
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        // 2. การเชื่อมต่อ Scroll ของพื้นที่ด้านล่างกลับขึ้นไปหา AppBar ด้านบน
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), 
        topBar = {
            // 3. ใช้ LargeTopAppBar (ขอบกว้าง) หรือ MediumTopAppBar แทน TopAppBar ปกติ
            LargeTopAppBar(
                title = {
                    Text(
                        "Mission 9: Collapsing",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Do something, เช่น สั่ง finish() ย้อนกลับ */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // นำ behavior ที่ตั้งค่าไว้มายัดใส่ลงใน AppBar
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Concept ของการทำ Collapsing Toolbar ใน Jetpack Compose\n\n" +
                               "ในโลกของ View ดั้งเดิม การทำแถบด้านบนยุบตัวเวลาเราเลื่อนหน้าจอลง (Collapsing Toolbar) มักจะต้องพึ่งพา AppBarLayout และ CoordinatorLayout ใน XML ซึ่งเขียนโค้ดค่อนข้างยุ่งยากและยาวพอสมควร\n\n" +
                               "แต่ใน Jetpack Compose (โดยเฉพาะเมื่อเราใช้ Material 3) การทำหน้าต่าง Collapsing กลายเป็นเรื่องง่ายนิดเดียว และโค้ดสั้นมากๆ ทำได้โดยโครงสร้าง 4 ขั้นตอน:\n\n" +
                               "1. เรียกใช้ LargeTopAppBar (หรือ MediumTopAppBar) แทนแถบ TopAppBar ปกติ\n\n" +
                               "2. สร้างตัวแปร scrollBehavior เพื่อนิยามพฤติกรรมการยุบตัว เช่น ใช้ TopAppBarDefaults.exitUntilCollapsedScrollBehavior() ซึ่งหมายถึงให้มันยุบตัวลงเมื่อเลื่อนจอ จนเหลือพ้ืนที่เล็กสุดก็หยุด\n\n" +
                               "3. ผูก scrollBehavior นี้เข้ากับ LargeTopAppBar ผ่าน property 'scrollBehavior'\n\n" +
                               "4. **จุดที่สำคัญมากสุด**: เอาตัวแปล scrollBehavior.nestedScrollConnection ไปใส่เป็นค่าผ่าน Modifier.nestedScroll() ให้กับ Scaffold (หรือ content หลักภายใน) เพื่อใช้ทฤษฎี NestedScroll Connection ส่งต่อพลังการใช้นิ้วเลื่อนจอกลับไปบอกให้ TopAppBar รู้ว่าเราเลื่อนนิ้วไปไกลเท่าไหร่แล้ว\n\n" +
                               "↓ ลองเอานิ้วไถข้อความนี้เพื่อเลื่อนจอลงด้านล่างดูเพื่อสังเกตการยุบตัวของ Navbar ด้านบนได้เลยคร้าบ! ↓",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            // เพิ่มเนื้อหาจำลองให้ยาวจนล้นจอ เพื่อสาธิตการ Scroll ขึ้น-ลง
            items(100) { index ->
                Text(
                    text = "เนื้อหาจำลองบรรทัดที่ ${index + 1}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

